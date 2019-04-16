package com.jaagro.report.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yj
 */
@Data
@Accessors(chain = true)
public class CustomerSettleFeeMonthly implements Serializable {
    private static final long serialVersionUID = -967102936881186669L;
    /**
     * 客户费用报表id
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 客户类型(1-个体客户,2-企业客户)
     */
    private Integer customerType;

    /**
     * 月份(yyyy-mm)
     */
    private String reportTime;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束日期
     */
    private Date endTime;

    /**
     * 运单总数
     */
    private Integer totalWaybill;

    /**
     * 总数量
     */
    private Integer totalQuantity;

    /**
     * 总重量(吨)
     */
    private BigDecimal totalWeight;

    /**
     * 总运费
     */
    private BigDecimal totalFreight;

    /**
     * 总异常费用
     */
    private BigDecimal totalAnomalyFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

}