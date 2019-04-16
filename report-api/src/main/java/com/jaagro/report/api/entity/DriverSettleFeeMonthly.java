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
public class DriverSettleFeeMonthly implements Serializable {
    private static final long serialVersionUID = 3723792482920534500L;
    /**
     * 司机费用报表id
     */
    private Integer id;

    /**
     * 司机id
     */
    private Integer driverId;

    /**
     * 司机名称
     */
    private String driverName;

    /**
     * 车辆id
     */
    private Integer truckId;

    /**
     * 车牌号
     */
    private String truckNumber;

    /**
     * 月份(yyyy-mm)
     */
    private String reportTime;

    /**
     * 起始时间
     */
    private Date startTime;

    /**
     * 结束时间
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