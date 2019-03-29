package com.jaagro.report.biz.mapper.cbs;

import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * PurchaseOrderItemsMapperExt接口
 *
 * @author :generator
 * @date :2019/3/9
 */
@Resource
public interface PurchaseOrderItemsMapperExt {

    /**
     * 累计采购订单数量
     *
     * @param purchaseOrderId
     * @return
     */
    BigDecimal calculateTotalPurchaseOrderQuantity(@Param("purchaseOrderId") Integer purchaseOrderId);

}