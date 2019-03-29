package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillTotalDto;
import com.jaagro.report.api.service.DataBigScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gavin
 * @Date 2019/3/27
 */
@Slf4j
@RestController
@RequestMapping("/bigData")
@Api(description = "数据大屏管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataBigScreenController {
    @Autowired
    private DataBigScreenService dataBigScreenService;

    /**
     * 客户贡献
     *
     * @return
     */
    @ApiOperation(value = "客户贡献前十")
    @GetMapping("/listTopTenCustomerData")
    public List<ContributionTopTenCustomerDto> listTopTenCustomerData() {
        List<ContributionTopTenCustomerDto> dataScreenDtoList = dataBigScreenService.listTopTenCustomerData();
        return dataScreenDtoList;
    }

    /**
     * 本季度运量统计
     *
     * @return
     */
    @ApiOperation(value = "本季度运量统计")
    @GetMapping("/listQuarterWaybill")
    public List<ListWaybillQuarterDto> listQuarterWaybill(@RequestParam String productType) {
        log.info("O listRedBlackBoardData productType: {}", productType);
        return dataBigScreenService.listQuarterWaybill(Integer.parseInt(productType));
    }

    /**
     * 历史运单汇总
     *
     * @return
     */
    @ApiOperation(value = "大区历史运单汇总")
    @GetMapping("/listHistoryWaybillByNetwork")
    public List<ListHistoryWaybillDto> listHistoryWaybillByNetwork() {
        return dataBigScreenService.listHistoryWaybill();
    }


    /**
     * 历史运单汇总
     *
     * @return
     */
    @ApiOperation(value = "项目部历史运单汇总")
    @GetMapping("/listHistoryWaybillByDept")
    public List<ListDeptHistoryWaybillDto> listHistoryWaybillByDept(@RequestParam String productType) {
        log.info("O listHistoryWaybillByDept productType: {}", productType);
        return dataBigScreenService.listHistoryWaybillByDept(Integer.parseInt(productType));
    }


    @ApiOperation(value = "司机红黑板数据列表")
    @GetMapping("/listRedBlackBoardData")
    public List<RedBlackBoardDto> listRedBlackBoardData(@RequestParam String boardType) {
        log.info("O listRedBlackBoardData boardType: {}", boardType);
        return dataBigScreenService.listRedBlackBoardData(boardType);
    }

    /**
     * 项目部统计运量数
     *
     * @param type: 1- 日报 2-月报
     * @return
     */
    @ApiOperation(value = "项目部统计运量数前十")
    @GetMapping("/listWaybillAmountByDept")
    public List<ListWaybillAmountDto> listWaybillAmountByDept(@RequestParam String type) {
        log.info("O listWaybillAmountByDept type: {}", type);
        return dataBigScreenService.listWaybillAmountByDept(Integer.parseInt(type));
    }

    /**
     * 大区运量数据
     *
     * @return
     */
    @ApiOperation(value = "大区运量数据")
    @GetMapping("/listWaybillByNetwork/{productType}")
    public List<ListDeptHistoryWaybillDto> listWaybillByNetwork(@PathVariable String productType) {
        return dataBigScreenService.listWaybillByNetwork(Integer.parseInt(productType));
    }

    /**
     * 当月运单异常情况
     *
     * @param
     * @return
     */
    @ApiOperation(value = "当月运单异常情况")
    @GetMapping("/listThisMonthWaybillAnomaly")
    public List<ListThisMonthWaybillAnomalyDto> listThisMonthWaybillAnomaly() {
        log.info("O listThisMonthWaybillAnomaly");
        return dataBigScreenService.listThisMonthWaybillAnomaly();
    }

    /**
     * 当月货物明细统计
     *
     * @param type: 1-日统计 2-月统计
     * @return
     */
    @ApiOperation(value = "当月货物明细统计-毛鸡")
    @GetMapping("/listWaybillCountChickenByType")
    public List<ListWaybillCountDto> listWaybillCountChickenByType(@RequestParam String type) {
        log.info("O listWaybillCountChickenByType  type: {}", type);
        return dataBigScreenService.listWaybillCountByProdTypeAndType("1", type);
    }

    @ApiOperation(value = "当月货物明细统计-饲料")
    @GetMapping("/listWaybillCountFoodByType")
    public List<ListWaybillCountDto> listWaybillCountFoodByType(@RequestParam String type) {
        log.info("O listWaybillCountFoodByType  type: {}", type);
        return dataBigScreenService.listWaybillCountByProdTypeAndType("2", type);
    }

    @ApiOperation(value = "当月货物明细统计-猪")
    @GetMapping("/listWaybillCountPigByType")
    public List<ListWaybillCountDto> listWaybillCountPigByType(@RequestParam String type) {
        log.info("O listWaybillCountPigByType  type: {}", type);
        return dataBigScreenService.listWaybillCountByProdTypeAndType("3", type);
    }

    /**
     * 数据大屏运量总和
     *
     * @param type: 1-日统计 2-月统计
     * @return
     */
    @ApiOperation(value = "数据大屏运量总和-毛鸡")
    @GetMapping("/listWaybillTotalChickenByType")
    public List<ListWaybillTotalDto> listWaybillTotalChickenByType(@RequestParam String type) {
        log.info("O listWaybillTotalChickenByType type: {}", type);
        return dataBigScreenService.listWaybillTotalByProdTypeAndType("1", type);
    }

    @ApiOperation(value = "数据大屏运量总和-饲料")
    @GetMapping("/listWaybillTotalFoodByType")
    public List<ListWaybillTotalDto> listWaybillTotalFoodByType(@RequestParam String type) {
        log.info("O listWaybillTotalFoodByType type: {}", type);
        return dataBigScreenService.listWaybillTotalByProdTypeAndType("2", type);
    }

    @ApiOperation(value = "数据大屏运量总和-猪")
    @GetMapping("/listWaybillTotalPigByType")
    public List<ListWaybillTotalDto> listWaybillTotalPigByType(@RequestParam String type) {
        log.info("O listWaybillTotalPigByType type: {}", type);
        return dataBigScreenService.listWaybillTotalByProdTypeAndType("3", type);
    }

    /**
     * 数据大屏货物同比
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "数据大屏货物同比-毛鸡")
    @GetMapping("/listTotalCompareChickenByType")
    public List<ListWaybillTotalDto> listTotalCompareChickenByType(@RequestParam String type) {
        log.info("O listTotalCompareChickenByType type: {}", type);
        return dataBigScreenService.listTotalCompareByProdTypeAndType("1", type);
    }

    @ApiOperation(value = "数据大屏货物同比-饲料")
    @GetMapping("/listTotalCompareFoodByType")
    public List<ListWaybillTotalDto> listTotalCompareFoodByType(@RequestParam String type) {
        log.info("O listTotalCompareFoodByType type: {}", type);
        return dataBigScreenService.listTotalCompareByProdTypeAndType("2", type);
    }

    @ApiOperation(value = "数据大屏货物同比-毛鸡")
    @GetMapping("/listTotalComparePigByType")
    public List<ListWaybillTotalDto> listTotalComparePigByType(@RequestParam String type) {
        log.info("O listTotalComparePigByType type: {}", type);
        return dataBigScreenService.listTotalCompareByProdTypeAndType("3", type);
    }

}
