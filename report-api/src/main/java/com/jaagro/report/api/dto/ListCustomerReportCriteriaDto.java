package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author baiyiran
 */
@Data
@Accessors(chain = true)
public class ListCustomerReportCriteriaDto implements Serializable {

    private static final long serialVersionUID = 6436182678970769680L;
    /**
     * 日期(yyyy-mm-dd)/月份(yyyy-mm)
     */
    private String reportTime;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 货物类型
     */
    private Integer goodsType;
}
