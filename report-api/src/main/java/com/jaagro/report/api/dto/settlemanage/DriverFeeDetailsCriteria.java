package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 司机结算费用详情
 * @author: @Gao.
 * @create: 2019-04-17 14:55
 **/
@Data
@Accessors(chain = true)
public class DriverFeeDetailsCriteria implements Serializable {

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
}
