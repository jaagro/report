package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 养殖计划信息
 * @author: @Gao.
 * @create: 2019-03-28 13:34
 **/
@Data
@Accessors(chain = true)
public class BreedingPlanInfoDo implements Serializable {
    /**
     * 养殖批次号
     */
    private String batchNo;
    /**
     * 上鸡计划
     */
    private Integer planChickenQuantity;
    /**
     * 鸡苗价格
     */
    private BigDecimal babyChickPrice;

    /**
     * 签订日期
     */
    private Date contractDate;

}
