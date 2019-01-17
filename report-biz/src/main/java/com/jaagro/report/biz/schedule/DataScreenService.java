package com.jaagro.report.biz.schedule;

import com.jaagro.report.api.constant.ReportDateType;
import com.jaagro.report.api.constant.ReportTaskType;
import com.jaagro.report.api.dto.ReturnDataScreenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 大屏数据
 *
 * @author baiyiran
 * @since 2019/01/17
 */
@Service
@Slf4j
public class DataScreenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 大屏value的key
     */
    private static final String key = "数据大屏value";
    /**
     * 大屏value的初始值
     */
    private final Integer valueFinal = 50717;


    @Scheduled(cron = "*/5 * * * * ?")
    public Integer doSomething() {
        Integer v;
        String value = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(value)) {
            v = Integer.parseInt(value);
            v = v + 20;
            redisTemplate.opsForValue().set(key, v.toString(), 30, TimeUnit.DAYS);
        } else {
            v = valueFinal + 20;
            redisTemplate.opsForValue().set(key, v.toString(), 30, TimeUnit.DAYS);
        }
        log.info("--大屏value:{}", v);
        return v;
    }

}
