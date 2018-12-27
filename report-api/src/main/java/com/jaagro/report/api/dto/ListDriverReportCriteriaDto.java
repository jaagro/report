package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yj
 * @since 2018/11/30
 */
@Data
@Accessors(chain = true)
public class ListDriverReportCriteriaDto implements Serializable{
    private static final long serialVersionUID = 4543210007780811672L;
    /**
     * 日期(yyyy-mm-dd)/月份(yyyy-mm)
     */
    private String reportTime;
    /**
     * 车牌号
     */
    private String truckNumber;
}
