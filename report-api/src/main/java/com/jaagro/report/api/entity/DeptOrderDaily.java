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
public class DeptOrderDaily implements Serializable {
    private static final long serialVersionUID = -425018666156406603L;
    /**
     * 订单数据报表id
     */
    private Integer id;

    /**
     * 日期(yyyy-mm-dd)
     */
    private String reportTime;

    /**
     * 项目部名称
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
     * 订单总数
     */
    private Integer totalOrderQuantity;

    /**
     * 运单总数
     */
    private Integer totalWaybillQuantity;

    /**
     * 完成运单数
     */
    private Integer completeWaybillQuantity;

    /**
     * 异常运单数
     */
    private Integer anomalyWaybillQuantity;

    /**
     * 拒单量
     */
    private Integer refuseWaybillQuantity;

    /**
     * 货物数量
     */
    private Integer goodsQuantity;

    /**
     * 货物重量
     */
    private BigDecimal goodsWeight;

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