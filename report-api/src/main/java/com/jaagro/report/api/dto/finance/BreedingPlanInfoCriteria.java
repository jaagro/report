package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 养殖计划查询条件
 * @author: @Gao.
 * @create: 2019-03-28 15:48
 **/
@Data
@Accessors(chain = true)
public class BreedingPlanInfoCriteria implements Serializable {
    /**
     * 批次号
     */
    private String batchNo;
}
