package com.henu.user.api;

import com.alibaba.fastjson.JSONObject;
import com.henu.common.util.JwtHelper;
import com.henu.common.util.Result;
import com.henu.common.util.ResultCodeEnum;
import com.henu.common.util.SumException;
import com.henu.model.models.user.UserInfo;
import com.henu.user.service.UserInfoService;
import com.henu.user.utils.ConstantWxPropertiesUtils;
import com.henu.user.utils.HttpClientUtils;
import java.net.URLEncoder;//com.sun.deploy.net.URLEncoder

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

//微信操作的接口
@Controller
@Slf4j
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {

    @Autowired
    private UserInfoService userInfoService;

    //微信扫描后回调的方法
    @GetMapping("callback")
    public String callback(String code,String state) {
        //第一步 获取临时票据 code
        System.out.println("code:"+code);
        //第二步 拿着code和微信id和秘钥，请求微信固定地址 ，得到两个值
        //使用code和appid以及appscrect换取access_token
        //  %s   占位符

        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {
            log.error("非法回调请求");
            throw new SumException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }

        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
                code);
        //使用httpclient请求这个地址
        String result = null;
        try {
            result = HttpClientUtils.get(accessTokenUrl);
        } catch (Exception e) {
            throw new SumException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        System.out.println("使用code换取的access_token结果 = " + result);

        JSONObject resultJson = JSONObject.parseObject(result);
        if(resultJson.getString("errcode") != null){
            log.error("获取access_token失败：" + resultJson.getString("errcode") + resultJson.getString("errmsg"));
            throw new SumException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
        }

        String accessToken = resultJson.getString("access_token");
        String openId = resultJson.getString("openid");
        log.info(accessToken);
        log.info(openId);

        // 根据access_token获取微信用户的基本信息
        // 先根据openid进行数据库查询
        UserInfo userInfo = userInfoService.selectWxInfoOpenId(openId);
        // 如果没有查到用户信息,那么调用微信个人信息获取的接口
        if(null == userInfo){
            //如果查询到个人信息，那么直接进行登录
            //使用access_token换取受保护的资源：微信的个人信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);
            String resultUserInfo = null;
            try {
                resultUserInfo = HttpClientUtils.get(userInfoUrl);
            } catch (Exception e) {
                throw new SumException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }
            System.out.println("使用access_token获取用户信息的结果 = " + resultUserInfo);

            JSONObject resultUserInfoJson = JSONObject.parseObject(resultUserInfo);
            if(resultUserInfoJson.getString("errcode") != null){
                log.error("获取用户信息失败：" + resultUserInfoJson.getString("errcode") + resultUserInfoJson.getString("errmsg"));
                throw new SumException(ResultCodeEnum.FETCH_USERINFO_ERROR);
            }

            //解析用户信息
            String nickname = resultUserInfoJson.getString("nickname");//昵称
            String headimgurl = resultUserInfoJson.getString("headimgurl");//头像

            userInfo = new UserInfo();
            userInfo.setOpenid(openId);
            userInfo.setNickName(nickname);
            userInfo.setStatus(1);
            userInfoService.save(userInfo);
        }

        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);
        // 判断userInfo是否有手机号，如果手机号为空，返回openid
        // 如果手机号不为空，返回openid值是空字符串
        if(StringUtils.isEmpty(userInfo.getPhone())) {
            map.put("openid", userInfo.getOpenid());
        } else {
            map.put("openid", "");
        }
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        return "redirect:" + ConstantWxPropertiesUtils.MEDICAL_BASE_URL + "/weixin/callback?token="+map.get("token")+"&openid="+map.get("openid")+"&name="+URLEncoder.encode((String)map.get("name"));
    }


    //1 生成微信扫描二维码
    //返回生成二维码需要参数
    @GetMapping("getLoginParam")
    @ResponseBody
    public Result genQrConnect() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
            map.put("scope","snsapi_login");
            String wxOpenRedirectUrl = ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL;
            wxOpenRedirectUrl = URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
            map.put("redirect_uri",wxOpenRedirectUrl);
            map.put("state",System.currentTimeMillis()+"");
            return Result.ok(map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
