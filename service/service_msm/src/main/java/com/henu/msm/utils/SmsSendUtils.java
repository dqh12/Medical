package com.henu.msm.utils;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Set;

public class SmsSendUtils {
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    public static final String ACCOUNT_SID = "8a216da884bbfa150184bd951b7a0146";

    public static final String ACCOUNT_TOKEN = "cbddcac304c74159b38ebb3cfce8b7db";

    public static final String ACCOUNT_APPID = "8a216da884bbfa150184bd951c93014d";

    public static void send(String code, String phone) {
        //生产环境请求地址：app.cloopen.com
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //请使用管理控制台中已创建应用的APPID
        //String appId = "appId";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(ACCOUNT_SID, ACCOUNT_TOKEN);
        sdk.setAppId(ACCOUNT_APPID);

        sdk.setBodyType(BodyType.Type_JSON);
        String templateId = "1";   //短信模板
        String[] datas = {code, "2"};   //分钟参数
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas);
        if ("000000".equals(result.get("statusCode"))) {
            //正常返回输出data包体信息（map）
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                System.out.println(key + " = " + object);
            }
        } else {
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }
    }

}
