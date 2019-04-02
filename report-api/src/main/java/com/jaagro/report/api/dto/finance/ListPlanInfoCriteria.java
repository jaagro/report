package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 养殖计划列表查询条件
 * @author: @Gao.
 * @create: 2019-03-29 14:36
 **/
@Data
@Accessors(chain = true)
public class ListPlanInfoCriteria {
    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 类型
     */
    private String type;
}
