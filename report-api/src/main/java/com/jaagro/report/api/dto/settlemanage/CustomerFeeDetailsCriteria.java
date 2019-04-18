package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yj
 * @date 2019/4/18 13:49
 */
@Data
@Accessors(chain = true)
public class CustomerFeeDetailsCriteria implements Serializable {
    /**
     * 起始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 运单号
     */
    private Integer waybillId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 项目部id
     */
    private Integer departmentId;
}
