package com.henu.sta.controller;

import com.henu.common.util.Result;
import com.henu.model.vo.order.OrderCountQueryVo;
import com.henu.order.client.OrderFeignClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Api(tags = "统计管理接口")
@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 获取订单统计数据
     * @param orderCountQueryVo
     * @return
     */
    @ApiOperation(value = "获取订单统计数据")
    @GetMapping("/getCountMap")
    public Result getCountMap(OrderCountQueryVo orderCountQueryVo) {
        Map<String, Object> map = orderFeignClient.getCountMap(orderCountQueryVo);
        return Result.ok(map);
    }
}
