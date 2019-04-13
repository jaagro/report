package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 养殖批次
 * @author: @Gao.
 * @create: 2019-03-28 16:47
 **/
@Data
@Accessors(chain = true)
public class BreedingPlanDetailsDo implements Serializable {

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 商品类型
     */
    private Integer productType;

    /**
     * 计划上鸡数量
     */
    private Integer planChickenQuantity;

    /**
     * 采购订单id
     */
    private Integer purchaseOrderId;

    /**
     * 签订日期
     */
    private Date contractDate;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     * 采购订单总金额
     */
    private BigDecimal totalPrice;
}
