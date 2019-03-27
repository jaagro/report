package com.jaagro.report.biz.mapper.tms;

import com.jaagro.report.api.dto.ListWaybillQuarterCriteriaDto;
import com.jaagro.report.api.dto.ListWaybillQuarterDto;
import com.jaagro.report.api.dto.ReturnDataScreenDto;
import com.jaagro.report.api.entity.DeptWaybillfeeDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 司机报表数据统计
 *
 * @author @Gao.
 * @since 2018/11/28
 */
public interface WaybillFeeReportMapperExt {
    /**
     * 根据时间查询日报表统计数据
     *
     * @param day
     * @return
     */
    List<DeptWaybillfeeDaily> listWaybillFeeStatisticsByDay(@Param("day") String day);

}
