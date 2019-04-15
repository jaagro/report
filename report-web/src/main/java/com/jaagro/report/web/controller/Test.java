package com.jaagro.report.web.controller;

import com.jaagro.report.api.service.WaybillFeeReportTaskService;
import com.jaagro.report.biz.mapper.report.UserLoginMapperExt;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tony
 */
@RestController
public class Test {

    @Autowired
    private UserLoginMapperExt userLoginMapper;
    @Autowired
    private WaybillFeeReportTaskService waybillFeeReportTaskService;

    @GetMapping("/test")
    public void test() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(date));
    }

    @GetMapping("/test1")
    public void test1() {
        //waybillFeeReportTaskService.createDailyReport(null);
        waybillFeeReportTaskService.createMonthlyReport(null);
    }

    public static void main(String args[]) {
        String reportTime = "2018-12-01 06:23:08";
        reportTime = reportTime.substring(0, 10);
        System.err.println(reportTime);
    }

    @Value("${feignClient.application.crm}")
    private String app;

    @GetMapping("/getApplicationYaml")
    public String getApplicationYaml(){
        return app;
    }
}
