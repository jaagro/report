package com.jaagro.report.biz.mapper.tms;


import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


/**
 * @author tony
 */
public interface WaybillCustomerFeeMapperExt {
    /**
     * 获取当前运单客户费用
     *
     * @return
     */
    BigDecimal accumulativeWaybillCustomerFee(@Param("waybillId") Integer waybillId);

    /**
     * 累计所有运单客户费用
     *
     * @param waybillId
     * @return
     */
    BigDecimal accumulativeWaybillCustomerAnomalyFee(@Param("waybillId") Integer waybillId);
}