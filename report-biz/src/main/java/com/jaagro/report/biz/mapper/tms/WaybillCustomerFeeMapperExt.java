package com.jaagro.report.biz.mapper.tms;


import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author tony
 */
public interface WaybillCustomerFeeMapperExt {
    /**
     * 获取当前运单客户费用
     *
     * @return
     */
    BigDecimal accumulativeWaybillCustomerFee(@Param("waybillIds") List<Integer> waybillIds);

    /**
     * 累计所有运单客户费用
     *
     * @param waybillIds
     * @return
     */
    BigDecimal accumulativeWaybillCustomerAnomalyFee(@Param("waybillIds") List<Integer> waybillIds);
}