package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.OrderReportDto;
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

}
