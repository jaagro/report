package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.dto.ListCustomerReportCriteriaDto;
import com.jaagro.report.api.entity.CustomerOrderDaily;
import com.jaagro.report.api.entity.CustomerOrderMonthly;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @since 2018/11/26
 */
public interface CustomerOrderDailyMapperExt extends CustomerOrderDailyMapper {
    /**
     * 物理删除当日报表数据
     *
     * @param reportTime
     */
    void deleteByReportTime(@Param("reportTime") String reportTime);

    /**
     * 批量插入当日报表数据
     *
     * @param customerDailyList
     */
    void batchInsert(@Param("customerDailyList") List<CustomerOrderDaily> customerDailyList);

    /**
     * 分页查询客户日报表
     *
     * @param dto
     * @return
     */
    List<CustomerOrderDaily> listCustomerDailyByCriteria(ListCustomerReportCriteriaDto dto);

    /**
     * @param beginMonth
     * @param endMonth
     * @return
     */
    List<CustomerOrderMonthly> listByBeginAndEndTime(@Param("beginMonth") String beginMonth, @Param("endMonth") String endMonth);

    /**
     * 数据大屏接口
     *
     * @return
     * @Author gavin
     * @Date 20190327
     */
    List<ContributionTopTenCustomerDto> listTopTenCustomerData();
}
