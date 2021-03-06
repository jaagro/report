package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.DriverReturnDto;
import com.jaagro.report.api.dto.TruckDto;
import com.jaagro.report.api.entity.DriverOrderDaily;
import com.jaagro.report.api.entity.DriverOrderMonthly;
import com.jaagro.report.api.service.DriverReportTaskService;
import com.jaagro.report.biz.mapper.report.DriverOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DriverOrderMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.DriverReportMapperExt;
import com.jaagro.report.biz.service.DriverClientService;
import com.jaagro.report.biz.service.TruckClientService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 司机报表任务服务
 *
 * @author yj
 * @since 2018/11/27
 */
@Service
@Slf4j
@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "driverOrderReport")
public class DriverReportTaskServiceImpl implements DriverReportTaskService {
    @Autowired
    private DriverReportMapperExt driverReportMapperExt;
    @Autowired
    private TruckClientService truckClientService;
    @Autowired
    private DriverClientService driverClientService;
    @Autowired
    private DriverOrderDailyMapperExt driverOrderDailyMapperExt;
    @Autowired
    private DriverOrderMonthlyMapperExt driverOrderMonthlyMapperExt;

    /**
     * 生成日报表
     *
     * @param day yyyy-MM-dd
     */
    @Override
    @CacheEvict(cacheNames = "driverOrderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createDailyReport(String day) {
        log.info("O createDailyReport day={}", day);
        createDriverOrderDailyReport(day);
    }

    /**
     * 生成月报表
     *
     * @param month yyyy-MM
     */
    @Override
    @CacheEvict(cacheNames = "driverOrderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createMonthlyReport(String month) {
        log.info("O createMonthlyReport month={}", month);
        createDriverOrderMonthlyReport(month);
    }

    /**
     * 异步生成日报表
     *
     * @param day yyyy-MM-dd
     */
    @Override
    @Async("reportExecutor")
    @CacheEvict(cacheNames = "driverOrderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createDailyReportAsync(String day) {
        log.info("O createDailyReportAsync day={}", day);
        createDriverOrderDailyReport(day);
    }

    /**
     * 异步生成月报表
     *
     * @param month yyyy-MM
     */
    @Override
    @Async("reportExecutor")
    @CacheEvict(cacheNames = "driverOrderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createMonthlyReportAsync(String month) {
        log.info("O createMonthlyReportAsync month={}", month);
        createDriverOrderMonthlyReport(month);
    }


    private void createDriverOrderDailyReport(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Set<DriverOrderDaily> driverOrderDailySet = new HashSet<>();
            Set<Integer> driverIdSet = new HashSet<>();
            Date beginDate = sdf.parse(day);
            Date endDate = DateUtils.addDays(beginDate, 1);
            // 接单量拒单量
            List<HashMap<String, Object>> recAndRefList = driverReportMapperExt.listRecAndRefCount(beginDate, endDate);
            // 异常费用
            List<HashMap<String, Object>> anomalyCostList = driverReportMapperExt.listAnomalyCost(beginDate, endDate);
            // 异常数
            List<HashMap<String, Object>> anomalyCountList = driverReportMapperExt.listAnomalyCount(beginDate, endDate);
            // 平均接单时长
            List<HashMap<String, Object>> avgRecDurationList = driverReportMapperExt.listAvgRecDuration(beginDate, endDate);
            // 完成量
            List<HashMap<String, Object>> completeCountList = driverReportMapperExt.listCompleteCount(beginDate, endDate);
            // 装货总运单量,准时数
            List<HashMap<String, Object>> loadTotalAndPunctualityList = driverReportMapperExt.listLoadTotalAndPunctuality(beginDate, endDate);
//             TODO 公里数 运费 费用合计
            // 合并查询结果集
            unionListToDailySet(driverIdSet, recAndRefList, anomalyCostList, anomalyCountList, avgRecDurationList, completeCountList, loadTotalAndPunctualityList);
            // 初始化日报表
            generateDailyReport(driverIdSet, driverOrderDailySet, day);
            // 设置接单量拒单量
            setDailyRecAndRef(driverOrderDailySet, recAndRefList);
            // 设置异常费用
            setDailyAnomalyCost(driverOrderDailySet, anomalyCostList);
            // 设置异常数
            setDailyAnomalyCount(driverOrderDailySet, anomalyCountList);
            // 设置平均接单时长
            setDailyAvgRecDuration(driverOrderDailySet, avgRecDurationList);
            // 设置完成量
            setDailyCompleteCount(driverOrderDailySet, completeCountList);
            // 设置装货准时率
            setDailyLoadPunctuality(driverOrderDailySet, loadTotalAndPunctualityList);
            // 司机订单日报表数据入库
            persistDailyDriverOrderDaily(driverOrderDailySet);
        } catch (Exception ex) {
            log.error("createDailyReport error day=" + day, ex);
        }
    }

    private void createDriverOrderMonthlyReport(String month) {
        //月报表数据从日报表中获取统计
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date beginDate = sdf.parse(month);
            Date endDate = DateUtils.addMonths(beginDate, 1);
            String endMonth = sdf.format(endDate);
            List<DriverOrderMonthly> driverOrderMonthlyList = driverOrderDailyMapperExt.listByBeginTimeAndEndTime(month, endMonth);
            if (!CollectionUtils.isEmpty(driverOrderMonthlyList)) {
                // 物理删除原有日报表
                driverOrderMonthlyMapperExt.deleteByReportTime(month);
                // 重新插入
                driverOrderMonthlyMapperExt.batchInsert(driverOrderMonthlyList);
            }
        } catch (Exception ex) {
            log.error("createMonthlyReport error month=" + month, ex);
        }
    }

    private void generateDailyReport(Set<Integer> driverIdSet, Set<DriverOrderDaily> driverOrderDailySet, String day) {
        if (!CollectionUtils.isEmpty(driverIdSet)) {
            for (Integer driverId : driverIdSet) {
                DriverOrderDaily orderDaily = new DriverOrderDaily();
                orderDaily
                        .setCreateTime(new Date())
                        .setEnabled(true)
                        .setReportTime(day)
                        .setDriverId(driverId)
                        .setLoadPunctualityRate(new BigDecimal("0"))
                        .setCompleteWaybillQuantity(0)
                        .setAnomalyWaybillQuantity(0)
                        .setReceiveWaybillQuantity(0)
                        .setAnomalyCost(new BigDecimal("0"))
                        .setRefuseWaybillQuantity(0);
                if (driverOrderDailySet != null) {
                    driverOrderDailySet.add(orderDaily);
                }
            }
            // 设置司机名称
            Set<Integer> truckIdSet = new HashSet<>();
            BaseResponse<List<DriverReturnDto>> driverResponse = driverClientService.listDriverByIds(new ArrayList<>(driverIdSet));
            if (ResponseStatusCode.OPERATION_SUCCESS.getCode() == driverResponse.getStatusCode()) {
                List<DriverReturnDto> driverReturnDtos = driverResponse.getData();
                if (!CollectionUtils.isEmpty(driverReturnDtos) && !CollectionUtils.isEmpty(driverOrderDailySet)) {
                    for (DriverReturnDto driverInfo : driverReturnDtos) {
                        for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                            if (driverInfo.getId().equals(orderDaily.getDriverId())) {
                                orderDaily
                                        .setDriverName(driverInfo.getName())
                                        .setTruckId(driverInfo.getTruckId());
                                if (driverInfo.getTruckId() != null) {
                                    truckIdSet.add(driverInfo.getTruckId());
                                }
                            }
                        }
                    }
                }
            }
            // 设置车牌号
            BaseResponse<List<TruckDto>> truckBaseResponse = truckClientService.listTruckByIds(new ArrayList<>(truckIdSet));
            if (ResponseStatusCode.OPERATION_SUCCESS.getCode() == truckBaseResponse.getStatusCode()) {
                List<TruckDto> truckDtoList = truckBaseResponse.getData();
                if (!CollectionUtils.isEmpty(truckDtoList) && !CollectionUtils.isEmpty(driverOrderDailySet)) {
                    for (TruckDto truckDto : truckDtoList) {
                        for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                            if (orderDaily.getTruckId().equals(truckDto.getId())) {
                                orderDaily.setTruckNumber(truckDto.getTruckNumber());
                            }
                        }
                    }
                }
            }

        }
    }

    private Set<Integer> unionListToDailySet(Set<Integer> driverIdSet, List<HashMap<String, Object>>... list) {
        if (list != null && list.length > 0) {
            for (List<HashMap<String, Object>> subList : list) {
                if (!CollectionUtils.isEmpty(subList)) {
                    for (HashMap<String, Object> result : subList) {
                        if (!result.isEmpty() && result.get("driver_id") != null) {
                            driverIdSet.add(Integer.valueOf(result.get("driver_id").toString()));
                        }
                    }
                }
            }
        }
        return driverIdSet;
    }

    private void setDailyRecAndRef(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> recAndRefList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(recAndRefList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> recAndRef : recAndRefList) {
                    if (!CollectionUtils.isEmpty(recAndRef)) {
                        if (orderDaily.getDriverId().toString().equals(recAndRef.get("driver_id") == null ? null : recAndRef.get("driver_id").toString())) {
                            if (recAndRef.get("receive_waybill_quantity") != null) {
                                orderDaily.setReceiveWaybillQuantity(Integer.valueOf(recAndRef.get("receive_waybill_quantity").toString()));
                            }
                            if (recAndRef.get("refuse_waybill_quantity") != null) {
                                orderDaily.setRefuseWaybillQuantity(Integer.valueOf(recAndRef.get("refuse_waybill_quantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void setDailyAnomalyCost(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> anomalyCostList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(anomalyCostList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> anomalyCost : anomalyCostList) {
                    if (!CollectionUtils.isEmpty(anomalyCost)) {
                        if (orderDaily.getDriverId().toString().equals(anomalyCost.get("driver_id") == null ? null : anomalyCost.get("driver_id").toString())) {
                            if (anomalyCost.get("anomaly_cost") != null) {
                                orderDaily.setAnomalyCost(new BigDecimal(anomalyCost.get("anomaly_cost").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void setDailyAnomalyCount(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> anomalyCountList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(anomalyCountList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> anomalyCount : anomalyCountList) {
                    if (!CollectionUtils.isEmpty(anomalyCount)) {
                        if (orderDaily.getDriverId().toString().equals(anomalyCount.get("driver_id") == null ? null : anomalyCount.get("driver_id").toString())) {
                            if (anomalyCount.get("anomaly_waybill_quantity") != null) {
                                orderDaily.setAnomalyWaybillQuantity(Integer.valueOf(anomalyCount.get("anomaly_waybill_quantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void setDailyAvgRecDuration(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> avgRecDurationList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(avgRecDurationList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> avgRecDuration : avgRecDurationList) {
                    if (!CollectionUtils.isEmpty(avgRecDuration)) {
                        if (orderDaily.getDriverId().toString().equals(avgRecDuration.get("driver_id") == null ? null : avgRecDuration.get("driver_id").toString())) {
                            if (avgRecDuration.get("avg_receive_duration") != null) {
                                BigDecimal avgReceiveDuration = new BigDecimal(avgRecDuration.get("avg_receive_duration").toString());
                                orderDaily.setAvgReceiveDuration(avgReceiveDuration.divide(new BigDecimal("60"), 2, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                }
            }
        }
    }

    private void setDailyCompleteCount(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> completeCountList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(completeCountList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> completeCount : completeCountList) {
                    if (!CollectionUtils.isEmpty(completeCount)) {
                        if (orderDaily.getDriverId().toString().equals(completeCount.get("driver_id") == null ? null : completeCount.get("driver_id").toString())) {
                            if (completeCount.get("complete_waybill_quantity") != null) {
                                orderDaily.setCompleteWaybillQuantity(Integer.valueOf(completeCount.get("complete_waybill_quantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    private void setDailyLoadPunctuality(Set<DriverOrderDaily> driverOrderDailySet, List<HashMap<String, Object>> loadTotalAndPunctualityList) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet) && !CollectionUtils.isEmpty(loadTotalAndPunctualityList)) {
            for (DriverOrderDaily orderDaily : driverOrderDailySet) {
                for (HashMap<String, Object> loadTotalAndPunctuality : loadTotalAndPunctualityList) {
                    if (!CollectionUtils.isEmpty(loadTotalAndPunctuality)) {
                        if (orderDaily.getDriverId().toString().equals(loadTotalAndPunctuality.get("driver_id") == null ? null : loadTotalAndPunctuality.get("driver_id").toString())) {
                            if (loadTotalAndPunctuality.get("punctuality") != null && loadTotalAndPunctuality.get("total") != null) {
                                BigDecimal punctuality = new BigDecimal(loadTotalAndPunctuality.get("punctuality").toString());
                                BigDecimal total = new BigDecimal(loadTotalAndPunctuality.get("total").toString());
                                if (total.compareTo(new BigDecimal("0")) > 0) {
                                    orderDaily.setLoadPunctualityRate(punctuality.divide(total, 2, BigDecimal.ROUND_HALF_UP));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void persistDailyDriverOrderDaily(Set<DriverOrderDaily> driverOrderDailySet) {
        if (!CollectionUtils.isEmpty(driverOrderDailySet)) {
            List<DriverOrderDaily> driverOrderDailyList = new ArrayList<>(driverOrderDailySet);
            // 物理删除当日报表数据
            driverOrderDailyMapperExt.deleteByReportTime(driverOrderDailyList.get(0).getReportTime());
            // 批量插入当日报表数据
            driverOrderDailyMapperExt.batchInsert(driverOrderDailyList);
        }
    }
}
