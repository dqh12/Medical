import com.alibaba.fastjson.JSONObject;
import com.henu.hospital.mapper.HospitalSetMapper;
import com.henu.hospital.mapper.ScheduleMapper;
import com.henu.hospital.model.HospitalSet;
import com.henu.hospital.service.ApiService;
import com.henu.hospital.util.HttpRequestHelper;
import com.henu.hospital.util.MedicalException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class Test01 {
    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    HospitalSetMapper hospitalSetMapper;

    @Autowired
    ApiService apiService;
    @Test
    public void test(){
        int pageNum = 1;
        int pageSize=15;
        Map<String, Object> result = new HashMap();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hoscode","1000_0");//hospitalSetMapper.selectById(1).getHoscode()
        //paramMap.put("depcode",depcode);
        paramMap.put("page",pageNum);
        paramMap.put("limit",pageSize);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", HttpRequestHelper.getSign(paramMap, hospitalSetMapper.selectById(1).getSignKey()));
        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,hospitalSetMapper.selectById(1).getApiUrl()+"/api/hosp/schedule/list");
        System.out.println(respone.toJSONString());
        if(null != respone && 200 == respone.getIntValue("code")) {
            JSONObject jsonObject = respone.getJSONObject("data");

            result.put("total", jsonObject.getLong("totalElements"));
            result.put("pageNum", pageNum);
            result.put("list", jsonObject.getJSONArray("content"));
        } else {
            throw new MedicalException(respone.getString("message"), 201);
        }
        System.out.println(result);
    }

    @Test
    public void test2() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        System.out.println(hospitalSet.getHoscode());
    }
}
