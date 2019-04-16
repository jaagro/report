package com.jaagro.report.biz.mapper.tms;

import com.jaagro.report.api.dto.waybill.GetWaybillGoodsDto;

import java.util.List;

/**
 * @author tony
 */
public interface WaybillGoodsMapperExt  {

    /**
     * 根据运单id获取所有goods列表
     *
     * @param waybillId
     * @return
     */
    List<GetWaybillGoodsDto> listGoodsByWaybillId(Integer waybillId);



}