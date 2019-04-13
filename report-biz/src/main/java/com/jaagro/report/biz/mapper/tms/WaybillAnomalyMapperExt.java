package com.jaagro.report.biz.mapper.tms;

import com.jaagro.report.api.dto.ListThisMonthWaybillAnomalyDto;
import com.jaagro.report.api.entity.DeptWaybillfeeDaily;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author baiyiran
 * @date  2019-3-28
 */
public interface WaybillAnomalyMapperExt {
    /**
     * 根据时间查询日报表统计数据
     *
     * @param day
     * @return
     */
    List<ListThisMonthWaybillAnomalyDto> listThisMonthWaybillAnomaly();

}
