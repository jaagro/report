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
     *
     * @param productType:货物类型 1-毛鸡；2-饲料；3-猪
     * @param type: 1-日统计 2-月统计
     * @return
     */
    @ApiOperation(value = "当月货物明细统计")
    @GetMapping("/listWaybillCountByProdTypeAndType")
    public List<ListWaybillCountDto> listWaybillCountByProdTypeAndType(@RequestParam String productType, @RequestParam String type) {
        log.info("O listWaybillCountByProdTypeAndType productType:{}  type: {}",productType, type);
        return dataBigScreenService.listWaybillCountByProdTypeAndType(productType,type);
    }

    /**
     *
     * @param productType:货物类型 1-毛鸡；2-饲料；3-猪
     * @param type: 1-日统计 2-月统计
     * @return
     */
    @ApiOperation(value = "数据大屏运量总和")
    @GetMapping("/listWaybillTotalByProdTypeAndType")
    public List<ListWaybillTotalDto> listWaybillTotalByProdTypeAndType(@RequestParam String productType, @RequestParam String type) {
        log.info("O listWaybillTotalByProdTypeAndType productType:{}  type: {}",productType, type);
        return dataBigScreenService.listWaybillTotalByProdTypeAndType(productType,type);
    }

    @ApiOperation(value = "数据大屏货物同比")
    @GetMapping("/listTotalCompareByProdTypeAndType")
    public List<ListWaybillTotalDto> listTotalCompareByProdTypeAndType(@RequestParam String productType, @RequestParam String type) {
        log.info("O listTotalCompareByProdTypeAndType productType:{}  type: {}",productType, type);
        return dataBigScreenService.listTotalCompareByProdTypeAndType(productType,type);
    }

}
