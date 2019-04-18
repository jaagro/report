package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 结算管理运单费用查询条件
 * @author: @Gao.
 * @create: 2019-04-11 17:02
 **/
@Data
@Accessors(chain = true)
public class WaybillFeeCriteria implements Serializable {
    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 运单号
     */
    private Integer waybillId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 车牌号
     */
    private String truckNumber;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 货物类型
     */
    private Integer goodsType;

    /**
     * 司机id
     */
    private Integer driverId;

    /**
     * 起始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 项目部id
     */
    private Integer departmentId;

    /**
     * 车辆id
     */
    private List<Integer> truckIds;

    /**
     * 客户id
     */
    private List<Integer> customerIds;

    /**
     * 网点id
     */
    private List<Integer> networkIds;
}
