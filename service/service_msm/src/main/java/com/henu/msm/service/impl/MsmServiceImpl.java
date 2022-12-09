package com.henu.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.henu.model.vo.msm.MsmVo;
import com.henu.msm.service.MsmService;
import com.henu.msm.utils.SmsSendUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String phone, String code) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
        try {
            SmsSendUtils.send(code,phone);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    //mq发送短信封装
    @Override
    public boolean send(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getPhone())) {
            boolean isSend = this.send(msmVo.getPhone(), msmVo.getParam());
            return isSend;
        }
        return false;
    }

    private boolean send(String phone, Map<String,Object> param) {
        //判断手机号是否为空
        if(StringUtils.isEmpty(phone)) {
            return false;
        }
            try {
                String code = JSONObject.toJSONString(param);
                SmsSendUtils.send(code,phone);
                return true;
                //获取response的body
                //System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
}
