package com.jaagro.report.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yj
 * @since 20181126
 */
@Data
@Accessors(chain = true)
public class CustomerOrderDaily implements Serializable {
    private static final long serialVersionUID = 4138620486602888871L;
    /**
     * 客户报表id
     */
    private Integer id;

    /**
     * 日期(yyyy-mm-dd)
     */
    private String reportTime;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 订单总数
     */
    private Integer orderQuantity;

    /**
     * 运单总数
     */
    private Integer waybillQuantity;

    /**
     * 异常单数
     */
    private Integer anomalyWaybillQuantity;

    /**
     * 货物类型
     */
    private Integer goodsType;

    /**
     * 数量(单位)
     */
    private Integer amount;

    /**
     * 吨位(单位)
     */
    private BigDecimal tonnage;

    /**
     * 收入-运费
     */
    private BigDecimal incomeFreight;

    /**
     * 收入-异常费用
     */
    private BigDecimal incomeAnomalyCost;

    /**
     * 成本-运费
     */
    private BigDecimal expendFreight;

    /**
     * 成本-异常费用
     */
    private BigDecimal expendAnomalyCost;

    /**
     * 毛利
     */
    private BigDecimal grossProfit;

    /**
     * 毛利率
     */
    private BigDecimal grossProfitRate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}