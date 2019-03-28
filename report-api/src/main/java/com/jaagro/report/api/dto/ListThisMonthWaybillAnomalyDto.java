package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 当月运单异常情况返回Dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListThisMonthWaybillAnomalyDto implements Serializable {

    /**
     * 异常日期
     */
    private String date;

    /**
     * 车牌号码
     */
    private String truckNumber;

    /**
     * 异常类型
     */
    private String type;

    /**
     * 异常描述
     */
    private String desc;
}
