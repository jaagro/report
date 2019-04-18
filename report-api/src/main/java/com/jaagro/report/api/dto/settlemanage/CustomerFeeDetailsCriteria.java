package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
     * 起始页
     */
    @NotNull(message = "{pageNum.NotNull}")
    @Min(value = 1,message = "{pageNum.Min}")
    private Integer pageNum;

    /**
     * 每页条数
     */
    @NotNull(message = "{pageSize.NotNull}")
    @Min(value = 1,message = "{pageSize.Min}")
    private Integer pageSize;

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
