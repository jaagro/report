package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
     * 运单号
     */
    private Integer waybillId;

    /**
     * 货物类型
     */
    private Integer goodsType;
}
