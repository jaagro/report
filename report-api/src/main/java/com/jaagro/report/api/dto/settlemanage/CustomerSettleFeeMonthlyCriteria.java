package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author yj
 * @date 2019/4/17 10:28
 */
@Data
@Accessors(chain = true)
public class CustomerSettleFeeMonthlyCriteria implements Serializable{
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
     * 月份(yyyy-MM)
     */
    private String month;
    /**
     * 客户名称
     */
    private String customerName;
}
