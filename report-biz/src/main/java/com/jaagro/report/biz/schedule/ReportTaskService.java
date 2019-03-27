package com.jaagro.report.biz.schedule;

import com.jaagro.report.api.constant.ReportDateType;
import com.jaagro.report.api.constant.ReportTaskType;
import com.jaagro.report.api.dto.ReportTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.jaagro.report.biz.config.RabbitMqConfig.TOPIC_EXCHANGE;

/**
 * 报表定时将报表任务放入RabbitMq队列
 *
 * @author yj
 * @since 2018/11/27
 */
@Service
@Slf4j
public class ReportTaskService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    private static final long EXPIRE = 24L;

    /**
     * 将司机日报表任务塞入mq队列
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void driverDailyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.DRIVER, ReportDateType.DAILY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S driverDailyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.DRIVER, ReportDateType.DAILY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将司机月报表任务塞入mq队列
     */
    @Scheduled(cron = "0 30 1 * * ?")
    public void driverMonthlyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.DRIVER, ReportDateType.MONTHLY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S driverMonthlyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.DRIVER, ReportDateType.MONTHLY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将客户日报表任务塞入mq队列
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void customerDailyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.CUSTOMER, ReportDateType.DAILY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S-customerDailyReportTaskToQueue-[reportTaskType={},reportDateType={}]", ReportTaskType.CUSTOMER, ReportDateType.DAILY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将客户月报表任务塞入mq队列
     */
    @Scheduled(cron = "0 30 2 * * ?")
    public void customerMonthlyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.CUSTOMER, ReportDateType.MONTHLY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S customerMonthlyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.CUSTOMER, ReportDateType.MONTHLY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将订单数据日报表任务塞入mq队列
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void deptOrderDailyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.ORDER, ReportDateType.DAILY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S deptOrderDailyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.ORDER, ReportDateType.DAILY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将订单数据月日报表任务塞入mq队列
     */

    @Scheduled(cron = "0 30 3 * * ?")
    public void deptOrderMonthlyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.ORDER, ReportDateType.MONTHLY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S deptOrderMonthlyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.ORDER, ReportDateType.MONTHLY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将运单费用日报表任务塞入mq队列
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void deptWaybillFeeDailyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.WAYBILL_FEE, ReportDateType.DAILY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S-deptWaybillFeeDailyReportTaskToQueue-[reportTaskType={},reportDateType={}]", ReportTaskType.WAYBILL_FEE, ReportDateType.DAILY);
            putToQueue(reportTaskDto);
        }
    }

    /**
     * 将运单费用月报表任务塞入mq队列
     */
    @Scheduled(cron = "0 30 4 * * ?")
    public void deptWaybillFeeMonthlyReportTaskToQueue() {
        ReportTaskDto reportTaskDto = new ReportTaskDto(ReportTaskType.WAYBILL_FEE, ReportDateType.MONTHLY);
        String redisKey = reportTaskDto.toString() + day.format(new Date());
        if (setIfAbsentAndExpire(redisKey, day.format(new Date()), EXPIRE)) {
            log.info("S deptWaybillFeeMonthlyReportTaskToQueue [reportTaskType={},reportDateType={}]", ReportTaskType.WAYBILL_FEE, ReportDateType.MONTHLY);
            putToQueue(reportTaskDto);
        }
    }

    private void putToQueue(ReportTaskDto reportTaskDto) {
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE, "report.send", reportTaskDto);
    }

    /**
     * spring-boot-data-redis 1.x版本无返回值不支持,2.x版本有返回值支持
     * @param redisKey
     * @param value
     * @param expire
     * @return
     */
    @Deprecated
    private boolean setIfAbsentAndExpireAtomic(String redisKey, String value, long expire) {
        try {
            Boolean result = (Boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                byte[] redisKeyBytes = redisTemplate.getKeySerializer().serialize(redisKey);
                byte[] valueBytes = redisTemplate.getValueSerializer().serialize(value);
                Expiration expiration = Expiration.from(expire, TimeUnit.HOURS);
                connection.set(redisKeyBytes, valueBytes, expiration, RedisStringCommands.SetOption.SET_IF_ABSENT);
                return true;
            });
            return result != null && result.booleanValue();
        } catch (Exception ex) {
            log.error(String.format("setIfAbsentAndExpire error redisKey=%s,value=%s,expire=%s", redisKey, value, expire), ex);
            return false;
        }
    }

    private boolean setIfAbsentAndExpire(String redisKey, String value, long expire) {
        try {
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            if (opsForValue.setIfAbsent(redisKey, value)) {
                stringRedisTemplate.expire(redisKey, expire, TimeUnit.HOURS);
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error(String.format("setIfAbsentAndExpire error redisKey=%s,value=%s,expire=%s", redisKey, value, expire), ex);
            return false;
        }
    }
}
