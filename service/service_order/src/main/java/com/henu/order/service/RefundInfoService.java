package com.henu.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.henu.model.models.order.PaymentInfo;
import com.henu.model.models.order.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}
