package com.jaagro.report.web.controller;

import com.jaagro.report.api.dto.OrderReportDto;
import com.jaagro.report.api.entity.DeptOrderDaily;
import com.jaagro.report.api.entity.DeptOrderMonthly;
import com.jaagro.report.api.service.OrderReportService;
import com.jaagro.utils.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author :gavin
 * @Date :20181128
 */
@RestController
@Api(description = "订单/运单报表生成", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class OrderReportController {

    @Autowired
    private OrderReportService orderReportService;

    /**
     * 订单日报表数据统计
     *
     * @param orderReportDto
     * @return
     */
    @ApiOperation("订单日报表数据统计")
    @PostMapping("/createOrderDailyReport")
    public BaseResponse<List<DeptOrderDaily>> createOrderDailyReport(@RequestBody OrderReportDto orderReportDto) {

        orderReportService.createDailyReport(orderReportDto);
        List<DeptOrderDaily> deptOrderDailyList = orderReportService.getDeptOrderDailyDataListFromTms(orderReportDto);

        return BaseResponse.successInstance(deptOrderDailyList);

    }

    @ApiOperation("订单月报表数据统计")
    @PostMapping("/createOrderMonthlyReport")
    public BaseResponse<List<DeptOrderMonthly>> createOrderMonthlyReport(@RequestBody OrderReportDto orderReportDto) {

        orderReportService.createMonthlyReport(orderReportDto);
        List<DeptOrderMonthly> deptOrderMonthlyList = orderReportService.getOrderMonthlyDataFromOrderDaily(orderReportDto);

        return BaseResponse.successInstance(deptOrderMonthlyList);

    }

    @ApiOperation("web查询订单日报表数据")
    @PostMapping("/queryOrderDailyReport")
    public BaseResponse<List<DeptOrderDaily>> queryOrderDailyReport(@RequestBody OrderReportDto orderReportDto) {

        List<DeptOrderDaily> deptOrderDailyList = orderReportService.listOrderDailyReport(orderReportDto);

        return BaseResponse.successInstance(deptOrderDailyList);

    }

    @ApiOperation("web查询订单月报表数据")
    @PostMapping("/queryOrderMonthlyReport")
    public BaseResponse<List<DeptOrderMonthly>> queryOrderMonthlyReport(@RequestBody OrderReportDto orderReportDto) {

        List<DeptOrderMonthly> deptOrderMonthlyList = orderReportService.listOrderMonthlyReport(orderReportDto);

        return BaseResponse.successInstance(deptOrderMonthlyList);

    }
}
