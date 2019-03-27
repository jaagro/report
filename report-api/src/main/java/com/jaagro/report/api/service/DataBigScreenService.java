package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.dto.ListDeptHistoryWaybillDto;
import com.jaagro.report.api.dto.ListHistoryWaybillDto;
import com.jaagro.report.api.dto.ListWaybillQuarterDto;

import java.util.List;


/**
 * @author baiyiran
 */
public interface DataBigScreenService {

    /**
     * 客户贡献前十
     */
    List<ContributionTopTenCustomerDto> listTopTenCustomerData();

    /**
     * 通过货物类型查询季度运单
     *
     * @param productType
     */
    List<ListWaybillQuarterDto> listQuarterWaybill(Integer productType);

    /**
     * 历史运单汇总
     *
     * @return
     */
    List<ListHistoryWaybillDto> listHistoryWaybill();

    /**
     * 项目部史运单汇总
     *
     * @param productType
     * @return
     */
    List<ListDeptHistoryWaybillDto> listHistoryWaybillByDept(Integer productType);
}
