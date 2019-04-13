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
public class DeptWaybillfeeDaily implements Serializable {
    private static final long serialVersionUID = -1660502928500817658L;
    /**
     * 运单费用报表id
     */
    private Integer id;

    /**
     * 日期(yyyy-mm-dd)
     */
    private String reportTime;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 部门id
     */
    private Integer departmentId;

    /**
     * 货物类型(1-毛鸡,2-饲料,3-母猪,4-公猪,5-仔猪,6-生猪)
     */
    private Integer goodsType;

    /**
     * 运单总数
     */
    private Integer totalWaybillQuantity;

    /**
     * 异常运单数
     */
    private Integer anomalyWaybillQuantity;

    /**
     * 货物数量
     */
    private Integer goodsQuantity;

    /**
     * 货物重量
     */
    private BigDecimal goodsWeight;

    /**
     * 收入-客户费用
     */
    private BigDecimal incomeCustomerFee;

    /**
     * 收入-异常费用
     */
    private BigDecimal incomeAnomalyFee;

    /**
     * 成本-运力费用
     */
    private BigDecimal expendFreight;

    /**
     * 成本-异常费用
     */
    private BigDecimal expendAnomalyFee;

    /**
     * 毛利
     */
    private BigDecimal grossProfit;

    /**
     * 毛利率
     */
    private BigDecimal grossProfitRate;

    /**
     * 环比
     */
    private BigDecimal chainRelativeRatio;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

}