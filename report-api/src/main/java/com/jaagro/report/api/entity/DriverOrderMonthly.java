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
public class DriverOrderMonthly implements Serializable{
    private static final long serialVersionUID = -2177175996851854170L;
    /**
     * 司机报表id
     */
    private Integer id;

    /**
     * 月份(yyyy-mm)
     */
    private String reportTime;

    /**
     * 司机名称
     */
    private String driverName;

    /**
     * 司机id
     */
    private Integer driverId;

    /**
     * 车牌号
     */
    private String truckNumber;

    /**
     * 车辆id
     */
    private Integer truckId;
    /**
     * 公里数
     */
    private BigDecimal mileage;

    /**
     * 接单量
     */
    private Integer receiveWaybillQuantity;

    /**
     * 拒单量
     */
    private Integer refuseWaybillQuantity;

    /**
     * 异常量
     */
    private Integer anomalyWaybillQuantity;

    /**
     * 完成运单数
     */
    private Integer completeWaybillQuantity;

    /**
     * 平均接单时长(单位分钟)
     */
    private BigDecimal avgReceiveDuration;

    /**
     * 装货准时率
     */
    private BigDecimal loadPunctualityRate;

    /**
     * 卸货准时率
     */
    private BigDecimal unloadPunctualityRate;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 异常费用
     */
    private BigDecimal anomalyCost;

    /**
     * 费用合计
     */
    private BigDecimal totalCost;

    /**
     * 是否有效(0-无效,1-有效)
     */
    private Boolean enabled;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;

    /**
     * 重写hashcode和equals用于比较对象相等
     *
     * @return
     * @author yj
     */
    @Override
    public int hashCode() {
        return driverId.hashCode() + reportTime.hashCode() + enabled.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        DriverOrderMonthly other = (DriverOrderMonthly) obj;
        return other.driverId.equals(this.driverId) && other.reportTime.equals(this.reportTime) && other.enabled.equals(this.enabled);
    }
}