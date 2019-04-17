package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.settlemanage.ListDriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.ReturnSettleDriverFeeMonthlyDto;
import com.jaagro.report.api.entity.DeptWaybillfeeMonthly;
import com.jaagro.report.api.entity.DriverSettleFeeMonthly;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 批量插入司机结算月报表数据
     *
     * @param driverSettleFeeMonthlyList
     */
    void batchSettleFeeMonthInsert(@Param("driverSettleFeeMonthlyList") List<DriverSettleFeeMonthly> driverSettleFeeMonthlyList);

    /**
     * 删除当前月司机结算报表
     *
     * @param reportTime
     */
    void deleteByReportTime(@Param("reportTime") String reportTime);


}
