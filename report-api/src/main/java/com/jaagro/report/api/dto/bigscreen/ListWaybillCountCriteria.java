package com.jaagro.report.api.dto.bigscreen;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 大屏运当月货物明细统计查询参数
 *
 * @author gavin
 */
@Accessors(chain = true)
@Data
public class ListWaybillCountCriteria implements Serializable {
    /**
     * 货物类型
     */
    String productType;
    /**
     * 1-日统计 2-月统计
     */
    String type;
    /**
     * 统计开始日期：YYYY-MM-DD
     */
    String strStartDate;
    /**
     *
     */
    String strEndDate;

}
