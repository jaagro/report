package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.*;
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
    public List<ListDeptHistoryWaybillDto> listHistoryWaybillByDept(@RequestParam int productType) {
        return dataBigScreenService.listHistoryWaybillByDept(productType);
    }


    @ApiOperation(value = "司机红黑板数据列表")
    @GetMapping("/listRedBlackBoardData")
    public List<RedBlackBoardDto> listRedBlackBoardData(@RequestParam String boardType) {
        return dataBigScreenService.listRedBlackBoardData(boardType);
    }
}
