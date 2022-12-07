package com.henu.user.api;

import com.henu.common.util.AuthContextHolder;
import com.henu.common.util.Result;
import com.henu.model.models.user.UserInfo;
import com.henu.model.vo.user.LoginVo;
import com.henu.model.vo.user.UserAuthVo;
import com.henu.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    //用户手机号登录接口
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
        //传递两个参数，第一个参数用户id，第二个参数认证数据vo对象，UserAuthVo包含了身份证信息
        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    //获取用户id信息接口
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);//从request的header获取token，根据token获得用户id
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);
    }
}
