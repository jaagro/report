package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.OrderReportDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountCriteria;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillTotalDto;
import com.jaagro.report.api.entity.DeptOrderMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gavin
 * @since 2018/11/26
 */
public interface DeptOrderMonthlyMapperExt extends DeptOrderMonthlyMapper {

    /**
     * @param list
     * @return
     */
    int batchInsert(List<DeptOrderMonthly> list);

    /**
     * @param reportTime
     * @return
     */

    int batchDelete(@Param("reportTime") String reportTime);

    /**
     * web查询月报表数据
     *
     * @param dto
     * @return
     */
    List<DeptOrderMonthly> listOrderMonthlyReport(OrderReportDto dto);

    /**
     * 数据大屏-当月货物明细统计
     * @param countCriteria
     * @return
     */
    List<ListWaybillCountDto> listWaybillCountByProdTypeAndType(ListWaybillCountCriteria countCriteria);

    /**
     * 数据大屏-本月运量总和
     * @param countCriteria
     * @return
     */
    List<ListWaybillTotalDto> listWaybillTotalByProdTypeAndType(ListWaybillCountCriteria countCriteria);

    /**
     * 数据大屏-运量月环比：本月总和上个月总和
     * @param countCriteria
     * @return
     */
    List<ListWaybillTotalDto>  listTotalCompareByProdTypeAndType (ListWaybillCountCriteria countCriteria);
}
