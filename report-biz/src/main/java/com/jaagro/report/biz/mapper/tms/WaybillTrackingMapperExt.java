package com.jaagro.report.biz.mapper.tms;


import com.jaagro.report.api.dto.waybill.WaybillTracking;
import org.apache.ibatis.annotations.Param;

/**
 * @author tony
 */
public interface WaybillTrackingMapperExt {
    /**
     * 获取装货地信息
     *
     * @param waybillId
     * @return
     */
    WaybillTracking getUnLoadSiteInfoByWaybillId(@Param("waybillId") Integer waybillId);

    /**
     * 获取装货地信息
     *
     * @param waybillId
     * @return
     */
    WaybillTracking getLoadSiteInfoByWaybillId(@Param("waybillId") Integer waybillId);
}