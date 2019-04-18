package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description:返回司机基本信息
 * @author: @Gao.
 * @create: 2019-04-16 15:26
 **/
@Data
@Accessors(chain = true)
public class ReturnDriverInfoDto {
    /**
     * 司机id
     */
    private Integer driverId;
    /**
     * 司机姓名
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
     * 车牌号
     */
    private Integer waybillId;

    /**
     * 运单数
     */
    private Integer waybillAmount;
}
