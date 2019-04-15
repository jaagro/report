package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 采购订单明细
 * @author: @Gao.
 * @create: 2019-04-01 11:42
 **/
@Data
@Accessors(chain = true)
public class ReturnPurchaseOrderDto implements Serializable {
    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 商品类型
     */
    private String productType;

    /**
     *
     */
    private Integer orderPhase;

    /**
     *
     */
    private String purchaseName;

    /**
     *
     */
    private BigDecimal totalPrice;

    /**
     * 单位
     */
    private String unit;

    /**
     * 购买数量
     */
    private BigDecimal quantity;

    /**
     * 采购时间
     */
    private Date purchaseTime;
}
