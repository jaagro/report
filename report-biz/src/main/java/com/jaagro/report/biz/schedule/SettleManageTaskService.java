package com.jaagro.report.biz.schedule;

import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.report.api.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 结算管理定时
 *
 * @author yj
 * @date 2019/4/16 15:39
 */
@Slf4j
@Service
public class SettleManageTaskService {
    @Autowired
    private SettleManageService settleManageService;

    /**
     * 定时生成客户结算月度费用报表
     */
    //@Scheduled( cron = "0 0/1 * * * ? ")
    public void createCustomerSettleFeeMonthly() {
        log.info("SettleManageTaskService.createCustomerSettleFeeMonthly begin");
        long start = System.currentTimeMillis();
        settleManageService.createCustomerSettleFeeMonthly(DateUtil.formatDate(new Date()));
        long end = System.currentTimeMillis();
        log.info("SettleManageTaskService.createCustomerSettleFeeMonthly end use time=" + (end - start));
    }
}
