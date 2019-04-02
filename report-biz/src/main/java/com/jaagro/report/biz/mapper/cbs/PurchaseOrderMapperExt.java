package com.jaagro.report.biz.mapper.cbs;


import com.jaagro.report.api.dto.finance.PurchaseOrder;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * PurchaseOrderMapperExt接口
 * Ø
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface PurchaseOrderMapperExt {
    /**
     * 根据采购订单查询采购单信息
     *
     * @param purchaseNo
     * @return
     */
    PurchaseOrder selectByPurchaseNo(@Param("purchaseNo") String purchaseNo);

    /**
     * 根据养殖计划id 查询采购订单
     *
     * @param planId
     * @return
     */
    List<PurchaseOrder> listPurchaseOrderByPlanId(@Param("planId") Integer planId);
}