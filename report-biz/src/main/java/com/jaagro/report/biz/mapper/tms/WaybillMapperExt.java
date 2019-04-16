package com.jaagro.report.biz.mapper.tms;

import com.jaagro.report.api.dto.settlemanage.ReturnWaybillFeeDto;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;
import com.jaagro.report.api.entity.CustomerSettleFeeMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询客户结算费用月度报表
     * @param customerId
     * @param start
     * @param end
     * @return
     */
    Map<String,Object> selectByParams(@Param("customerId") Integer customerId, @Param("start") Date start, @Param("end") Date end);
}
