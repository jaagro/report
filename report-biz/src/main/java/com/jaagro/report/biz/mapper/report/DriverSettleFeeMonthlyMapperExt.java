package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.settlemanage.ListDriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.ReturnSettleDriverFeeMonthlyDto;
import com.jaagro.report.api.entity.DriverSettleFeeMonthly;

import java.util.List;

/**
 * @author yj
 * @date 2019/4/16 15:00
 */
public interface DriverSettleFeeMonthlyMapperExt extends DriverSettleFeeMonthlyMapper {

    /**
     * 获取司机结算报表
     *
     * @param criteria
     * @return
     */
    List<ReturnSettleDriverFeeMonthlyDto> selectByCriteria(ListDriverFeeCriteria criteria);

}
