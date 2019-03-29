package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.ListWaybillAmountDto;
import com.jaagro.report.api.dto.OrderReportDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountCriteria;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillTotalDto;
import com.jaagro.report.api.entity.DeptOrderDaily;
import com.jaagro.report.api.entity.DeptOrderMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Gavin
 * @since 2018/11/27
 */
public interface DeptOrderDailyMapperExt extends DeptOrderDailyMapper {
    /**
     * @param list
     * @return
     */

    int batchInsert(List<DeptOrderDaily> list);

    /**
     * @param reportTime
     * @return
     */
    int batchDelete(@Param("reportTime") String reportTime);

    /**
     * 从日报表里查询统计出月报表数据,用于把统计出来的数据插入到表dept_order_monthly
     *
     * @param orderReportDto
     * @return
     */
    List<DeptOrderMonthly> getOrderMonthlyDataFromOrderDaily(OrderReportDto orderReportDto);

    /**
     * web查询日报表数据
     *
     * @param dto
     * @return
     */
    List<DeptOrderDaily> listOrderDailyReport(OrderReportDto dto);

    /**
     * 项目部统计运量数
     *
     * @param type
     * @return
     */
    List<ListWaybillAmountDto> listWaybillAmountByDept(@Param("type") Integer type);


    /**
     * 数据大屏-当月货物明细统计
     * @param countCriteria
     * @return
     */
    List<ListWaybillCountDto> listWaybillCountByProdTypeAndType(ListWaybillCountCriteria countCriteria);

    /**
     * 数据大屏-昨天运量总和
     * @param countCriteria
     * @return
     */
    List<ListWaybillTotalDto> listWaybillTotalByProdTypeAndType(ListWaybillCountCriteria countCriteria);

    /**
     * 数据大屏-运量日环比：昨天总和减去前天总和
     * @param countCriteria
     * @return
     */
    List<ListWaybillTotalDto>  listTotalCompareByProdTypeAndType (ListWaybillCountCriteria countCriteria);


}
