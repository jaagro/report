package com.jaagro.report.biz.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;
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
    private static final String key = "数据大屏饲料value";
    /**
     * 大屏value的初始值
     */
    private final Integer valueFinal = 212174;
    /**
     * 毛鸡大屏value的key
     */
    private static final String chickenKey = "数据大屏value毛鸡";
    /**
     * 毛鸡大屏value的初始值
     */
    private final Integer chickenValueFinal = 9157510;

    /**
     * 饲料总量
     *
     * @return
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public Integer doSomething() {
        Integer v;
        String value = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(value)) {
            v = Integer.parseInt(value);
            v = v + new Random().nextInt(40 - 20 + 1) + 20;
            redisTemplate.opsForValue().set(key, v.toString(), 30, TimeUnit.DAYS);
        } else {
            v = valueFinal + new Random().nextInt(40 - 20 + 1) + 20;
            redisTemplate.opsForValue().set(key, v.toString(), 30, TimeUnit.DAYS);
        }
        return v;
    }

    /**
     * 毛鸡总量
     *
     * @return
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public Integer doSomethingChicken() {
        Integer v;
        String value = redisTemplate.opsForValue().get(chickenKey);
        if (!StringUtils.isEmpty(value)) {
            v = Integer.parseInt(value);
            v = v + new Random().nextInt(4500 - 4000 + 1) + 4000;
            redisTemplate.opsForValue().set(chickenKey, v.toString(), 30, TimeUnit.DAYS);
        } else {
            v = chickenValueFinal + new Random().nextInt(4500 - 4000 + 1) + 4000;
            redisTemplate.opsForValue().set(chickenKey, v.toString(), 30, TimeUnit.DAYS);
        }
        return v;
    }

}
