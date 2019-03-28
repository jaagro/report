package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.*;

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

    List<RedBlackBoardDto> listRedBlackBoardData(String boardType);

    /**
     * 项目部统计运量数
     *
     * @param type
     * @return
     */
    List<ListWaybillAmountDto> listWaybillAmountByDept(Integer type);

    /**
     * 当月运单异常情况
     *
     * @return
     */
    List<ListThisMonthWaybillAnomalyDto> listThisMonthWaybillAnomaly();
}
