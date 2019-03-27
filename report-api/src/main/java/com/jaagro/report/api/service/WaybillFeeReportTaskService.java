package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.entity.DeptWaybillfeeDaily;
import com.jaagro.report.api.entity.DeptWaybillfeeMonthly;

import java.util.List;

/**
 * 运单费用 日 月报表统计服务
 *
 * @author @Gao.
 **/
public interface WaybillFeeReportTaskService {
    /**
     * 生成日报表
     *
     * @param day yyyy-MM-dd
     */
    void createDailyReport(String day);

    /**
     * 生成月报表
     *
     * @param month yyyy-MM
     */
    void createMonthlyReport(String month);

    /**
     * 运单费用日报表列表
     *
     * @param dto
     * @return
     */
    List<DeptWaybillfeeDaily> listWaybillFeeDailyReport(WaybillFeeReportDto dto);

    /**
     * 运单费用月报表列表
     *
     * @param dto
     * @return
     */
    List<DeptWaybillfeeMonthly> listWaybillFeeMonthReport(WaybillFeeReportDto dto);

}
