package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.dto.ListHistoryWaybillDto;
import com.jaagro.report.api.dto.ListWaybillQuarterDto;
import com.jaagro.report.api.dto.ReturnDataScreenDto;
import com.jaagro.report.api.service.DataBigScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<ListWaybillQuarterDto> listQuarterWaybill(@RequestParam Integer productType) {
        return dataBigScreenService.listQuarterWaybill(productType);
    }

    /**
     * 历史运单汇总
     *
     * @return
     */
    @ApiOperation(value = "历史运单汇总")
    @GetMapping("/listHistoryWaybill")
    public List<ListHistoryWaybillDto> listHistoryWaybill() {
        return dataBigScreenService.listHistoryWaybill();
    }


}
