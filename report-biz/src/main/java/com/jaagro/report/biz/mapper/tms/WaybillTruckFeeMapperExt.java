package com.jaagro.report.biz.mapper.tms;



import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author tony
 */
public interface WaybillTruckFeeMapperExt {
    /**
     * 运力侧费用
     *
     * @param waybillId
     * @return
     */
    BigDecimal accumulativeWaybillTruckFee(@Param("waybillId") Integer waybillId);

    /**
     * 累计运单异常费用
     *
     * @param waybillId
     * @return
     */
    BigDecimal accumulativeWaybillTruckAnomalyFee(@Param("waybillId") Integer waybillId);
}