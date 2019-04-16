package com.jaagro.report.biz.mapper.tms;

import com.jaagro.report.api.dto.settlemanage.ReturnWaybillFeeDto;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;

import java.util.List;

/**
 * @author tony
 */
public interface WaybillMapperExt {
    /**
     * 查询运单结算
     *
     * @param criteria
     * @return
     */
    List<ReturnWaybillFeeDto> listSettleManageWaybillFee(WaybillFeeCriteria criteria);
}
