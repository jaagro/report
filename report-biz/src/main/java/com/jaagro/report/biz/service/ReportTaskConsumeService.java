package com.jaagro.report.biz.service;

import com.jaagro.report.api.constant.ReportDateType;
import com.jaagro.report.api.constant.ReportTaskType;
import com.jaagro.report.api.dto.OrderReportDto;
import com.jaagro.report.api.dto.ReportTaskDto;
import com.jaagro.report.api.service.CustomerReportTaskService;
import com.jaagro.report.api.service.DriverReportTaskService;
import com.jaagro.report.api.service.OrderReportService;
import com.jaagro.report.api.service.WaybillFeeReportTaskService;
import com.jaagro.report.biz.config.RabbitMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 报表任务消费
 *
 * @author yj
 * @since 2018/11/27
 */
@Service
@Slf4j
public class ReportTaskConsumeService {
    @Autowired
    private DriverReportTaskService driverReportTaskService;
    @Autowired
    private OrderReportService orderReportService;
    @Autowired
    private WaybillFeeReportTaskService waybillFeeReportTaskService;
    @Autowired
    private CustomerReportTaskService customerReportTaskService;


    @RabbitListener(queues = RabbitMqConfig.REPORT_SEND_QUEUE)
    private void consumeTask(ReportTaskDto reportTaskDto) {
        log.info("S consumeTask reportTaskDto={}", reportTaskDto);
        SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat month = new SimpleDateFormat("yyyy-MM");
        long begin = System.currentTimeMillis();
        if (ReportTaskType.DRIVER.equals(reportTaskDto.getTaskType())) {
            if (ReportDateType.DAILY.equals(reportTaskDto.getDateType())) {
                driverReportTaskService.createDailyReport(day.format(DateUtils.addDays(new Date(), -1)));
            }
            if (ReportDateType.MONTHLY.equals(reportTaskDto.getDateType())) {
                driverReportTaskService.createMonthlyReport(month.format(DateUtils.addDays(new Date(), -1)));
            }
        } else if (ReportTaskType.CUSTOMER.equals(reportTaskDto.getTaskType())) {
            if (ReportDateType.DAILY.equals(reportTaskDto.getDateType())) {
                customerReportTaskService.createDailyReport(day.format(DateUtils.addDays(new Date(), -1)));
            }
            if (ReportDateType.MONTHLY.equals(reportTaskDto.getDateType())) {
                customerReportTaskService.createMonthlyReport(month.format(DateUtils.addDays(new Date(), -1)));
            }
        } else if (ReportTaskType.ORDER.equals(reportTaskDto.getTaskType())) {
            if (ReportDateType.DAILY.equals(reportTaskDto.getDateType())) {
                OrderReportDto orderReportDto = new OrderReportDto();
                String dayString = day.format(DateUtils.addDays(new Date(), -1));
                orderReportDto.setReportTime(dayString);
                orderReportService.createDailyReport(orderReportDto);
            }
            if (ReportDateType.MONTHLY.equals(reportTaskDto.getDateType())) {
                OrderReportDto orderReportDto = new OrderReportDto();
                String monthString = month.format(DateUtils.addDays(new Date(), -1));
                orderReportDto.setReportTime(monthString);
                orderReportService.createMonthlyReport(orderReportDto);
            }

        } else if (ReportTaskType.WAYBILL_FEE.equals(reportTaskDto.getTaskType())) {
            if (ReportDateType.DAILY.equals(reportTaskDto.getDateType())) {
                String dayString = day.format(DateUtils.addDays(new Date(), -1));
                waybillFeeReportTaskService.createDailyReport(dayString);
            }
            if (ReportDateType.MONTHLY.equals(reportTaskDto.getDateType())) {
                String monthString = month.format(DateUtils.addDays(new Date(), -1));
                waybillFeeReportTaskService.createMonthlyReport(monthString);
            }
        }
        long end = System.currentTimeMillis();
        log.info("S consumeTask [useTime={},reportTaskDto={}]", (end - begin), reportTaskDto);
    }
}
