package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 司机结算费用列表
 * @author: @Gao.
 * @create: 2019-04-17 14:31
 **/
@Data
@Accessors(chain = true)
public class ReturnSettleDriverFeeMonthlyDto implements Serializable {

    /**
     * 司机id
     */
    private Integer driverId;
    /**
     * 司机名称
     */
    private String driverName;

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
}
