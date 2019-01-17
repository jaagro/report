package com.jaagro.report.biz.schedule;

import com.jaagro.report.api.constant.ReportDateType;
import com.jaagro.report.api.constant.ReportTaskType;
import com.jaagro.report.api.dto.ReturnDataScreenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 大屏数据
 *
 * @author baiyiran
 * @since 2019/01/17
 */
@Service
@Slf4j
public class DataScreenService {

    Integer value = 50717;

    @Scheduled(cron = "*/5 * * * * ?")
    public Integer doSomething() {
        value = value + 20;
        log.info("----数据大屏:{}", value);
        return value;
    }

}
