package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.entity.DeptWaybillfeeMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @since 2018/11/26
 */
public interface DeptWaybillfeeMonthlyMapperExt extends DeptWaybillfeeMonthlyMapper {
    /**
     * 批量插入月报表数据
     *
     * @param deptWaybillfeeMonthly
     */
    void batchWaybillFeeMonthInsert(@Param("deptWaybillfeeMonthly") List<DeptWaybillfeeMonthly> deptWaybillfeeMonthly);

    /**
     * 批量删除当月报表数据
     *
     * @param month
     */
    void batchDeleteWaybillFeeDailyByMonth(@Param("month") String month);

    /**
     * 运单费用月报表列表
     *
     * @param dto
     * @return
     */
    List<DeptWaybillfeeMonthly> listWaybillFeeMonthReport(WaybillFeeReportDto dto);

    /**
     * 通过货物类型查询季度运单
     *
     * @param criteriaDto
     * @return
     */
    Long listQuarterWaybill(ListWaybillQuarterCriteriaDto criteriaDto);

    /**
     * 大区历史运单汇总
     *
     * @param quarterDto
     * @return
     */
    List<ListHistoryWaybillDto> listHistoryWaybill(ListWaybillQuarterCriteriaDto quarterDto);

    /**
     * 项目部历史运单汇总
     *
     * @param criteriaDto
     * @return
     */
    List<ListDeptHistoryWaybillDto> listHistoryWaybillByDept(ListWaybillQuarterCriteriaDto criteriaDto);
}
