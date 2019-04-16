package com.jaagro.report.biz.mapper.tms;


import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author tony
 */
public interface WaybillTruckFeeMapperExt {
    /**
     * 运力侧费用
     *
     * @param waybillIds
     * @return
     */
    BigDecimal accumulativeWaybillTruckFee(@Param("waybillIds") List<Integer> waybillIds);

    /**
     * 累计运单异常费用
     *
     * @param waybillIds
     * @return
     */
    BigDecimal accumulativeWaybillTruckAnomalyFee(@Param("waybillIds") List<Integer> waybillIds);
}