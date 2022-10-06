package com.henu.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henu.model.models.order.OrderInfo;
import com.henu.model.vo.order.OrderCountQueryVo;
import com.henu.model.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<OrderInfo> {

    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
