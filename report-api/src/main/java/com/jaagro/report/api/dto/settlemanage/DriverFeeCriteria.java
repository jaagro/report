package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 司机费用
 * @author: @Gao.
 * @create: 2019-04-16 14:31
 **/
@Data
@Accessors(chain = true)
public class DriverFeeCriteria implements Serializable {

    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 司机id
     */
    private Integer driverId;
}
