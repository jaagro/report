package com.jaagro.report.api.dto.bigscreen;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 大屏运单量返回dto
 *
 * @author gavin
 */
@Accessors(chain = true)
@Data
public class ListWaybillCountDto implements Serializable {

    /**
     * 日 或者月份   格式： 年-月 / 月-日
     */
    private String x;

    /**
     * 汇总值
     */
    private BigDecimal y;
}
