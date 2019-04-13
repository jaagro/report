package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 运单量返回dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListWaybillAmountDto implements Serializable {

    private Integer departmentId;

    /**
     * 部门名称
     */
    private String x;

    /**
     * 量值
     */
    private String y;

    /**
     * 货物类型
     */
    private String z;
}
