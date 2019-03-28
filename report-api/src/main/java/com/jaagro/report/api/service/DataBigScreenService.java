package com.jaagro.report.api.service;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillTotalDto;

import java.util.List;


/**
 * @author baiyiran
 */
public interface DataBigScreenService {

    /**
     * 客户贡献前十
     * @return
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

    /**
     * 大屏红黑榜数据
     * @param boardType
     * @return
     */
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

    /**
     * 数据大屏当月货物明细统计
     * type:1 统计前5天该货物的总运量
     * type:2 统计前5个月货物的总运量
     * @param productType
     * @param type
     * @return
     */
    List<ListWaybillCountDto> listWaybillCountByProdTypeAndType(String productType, String type);

    /**
     * 数据大屏运量总和
     * @param productType
     * @param type
     * @return
     */
    List<ListWaybillTotalDto> listWaybillTotalByProdTypeAndType(String productType, String type);
}
