package com.jaagro.report.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 采购订单
 * @author: @Gao.
 * @create: 2019-04-01 14:48
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PurchaseOrderDo implements Serializable {

    /**
     * 采购订单id
     */
    private Integer purchaseOrderId;

    /**
     * 商品类型
     */
    private Integer productType;

    /**
     *
     */
    private Integer orderPhase;

    /**
     * 采购订单总金额
     */
    private BigDecimal totalPrice;

    /**
     * 商品采购单编号
     */
    private String purchaseNo;

    /**
     * 采购时间
     */
    private Date purchaseTime;

    /**
     *
     */
    private String purchaseName;
}
