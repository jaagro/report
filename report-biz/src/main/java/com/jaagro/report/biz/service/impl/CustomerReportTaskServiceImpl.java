package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.ListCustomerReportCriteriaDto;
import com.jaagro.report.api.dto.customer.ShowCustomerDto;
import com.jaagro.report.api.entity.CustomerOrderDaily;
import com.jaagro.report.api.entity.CustomerOrderMonthly;
import com.jaagro.report.api.service.CustomerReportTaskService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.CustomerOrderMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.CustomerReportMapperExt;
import com.jaagro.report.biz.service.CustomerClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author baiyiran
 */
@Service
@Slf4j
public class CustomerReportTaskServiceImpl implements CustomerReportTaskService {

    @Autowired
    private CustomerReportMapperExt customerReportMapperExt;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private CustomerOrderDailyMapperExt dailyMapperExt;
    @Autowired
    private CustomerOrderMonthlyMapperExt monthlyMapperExt;

    /**
     * 生成日报表
     *
     * @param day yyyy-MM-dd
     */
    @Override
    public void createDailyReport(String day) {
        createCustomerDaily(day);
    }

    /**
     * 生成月报表
     *
     * @param month yyyy-MM
     */
    @Override
    public void createMonthlyReport(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date beginDate = sdf.parse(month);
            Date endDate = DateUtils.addMonths(beginDate, 1);
            String endMonth = sdf.format(endDate);
            List<CustomerOrderMonthly> customerOrderMonthList = dailyMapperExt.listByBeginAndEndTime(month, endMonth);
            if (!CollectionUtils.isEmpty(customerOrderMonthList)) {
                // 物理删除原有客户日报表
                monthlyMapperExt.deleteByReportTime(month);
                for (CustomerOrderMonthly customerMonth : customerOrderMonthList) {
                    if (customerMonth != null) {
                        customerMonth.setCreateTime(new Date());
                    }
                }
                // 重新插入
                monthlyMapperExt.batchInsert(customerOrderMonthList);
            }
        } catch (Exception ex) {
            log.error("createMonthlyReport error month=" + month, ex);
        }
    }

    /**
     * 客户日报表列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<CustomerOrderDaily> listCustomerDailyReport(ListCustomerReportCriteriaDto dto) {
        if (!StringUtils.isEmpty(dto.getReportTime())) {
            String subStr = dto.getReportTime();
            subStr = subStr.substring(0, 10);
            dto.setReportTime(subStr);
        }
        return dailyMapperExt.listCustomerDailyByCriteria(dto);
    }

    /**
     * 客户月报表列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<CustomerOrderMonthly> listCustomerMonthReport(ListCustomerReportCriteriaDto dto) {
        if (!StringUtils.isEmpty(dto.getReportTime())) {
            String subStr = dto.getReportTime();
            subStr = subStr.substring(0, 7);
            dto.setReportTime(subStr);
        }
        return monthlyMapperExt.listCustomerMonthByCriteria(dto);
    }

    private void createCustomerDaily(String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Set<CustomerOrderDaily> customerDailySet = new HashSet<>();
            Set<HashMap<String, Integer>> customerIdAndTypeSet = new HashSet<>();
            Date beginDate = sdf.parse(day);
            Date endDate = DateUtils.addDays(beginDate, 1);
            //订单总数
            List<HashMap<String, Object>> orderCountSet = customerReportMapperExt.listOrderCount(beginDate, endDate);
            //运单总数
            List<HashMap<String, Object>> waybillCountSet = customerReportMapperExt.listWaybillCount(beginDate, endDate);
            //异常单数
            List<HashMap<String, Object>> anomalyCountSet = customerReportMapperExt.listAnomalyCount(beginDate, endDate);
            //数量(单位) && 吨位(单位)
            List<HashMap<String, Object>> amountAndTonSet = customerReportMapperExt.listAmmountAndTonn(beginDate, endDate);
            //收入-运费
            //收入-异常费用
            List<HashMap<String, Object>> incomeAnomalySet = customerReportMapperExt.ListIncomeAnomalyCost(beginDate, endDate);
            //成本-运费
            //成本-异常费用
            List<HashMap<String, Object>> expendAnomalySet = customerReportMapperExt.listExpendAnomalyCost(beginDate, endDate);
            //毛利
            List<HashMap<String, Object>> grossSet = customerReportMapperExt.listGrossProfit(beginDate, endDate);
            //毛利率

            //合并客户结果集
            unionListToDailySet(customerIdAndTypeSet, orderCountSet, waybillCountSet, anomalyCountSet, amountAndTonSet, incomeAnomalySet, expendAnomalySet, grossSet);
            //初始化
            generateDailyReport(customerIdAndTypeSet, customerDailySet, day);
            //初始化订单总数
            initOrderCount(customerDailySet, orderCountSet);
            //初始化运单总数
            initWaybillCount(customerDailySet, waybillCountSet);
            //初始化异常单数
            initAnomalyCount(customerDailySet, anomalyCountSet);
            //初始化数量(单位) && 吨位(单位)
            initAmmountAndTonn(customerDailySet, amountAndTonSet);
            //初始化收入-运费
            //初始化收入-异常费用
            initIncomeAnomalyCost(customerDailySet, incomeAnomalySet);
            //初始化成本-运费
            //初始化成本-异常费用
            initExpendAnomalyCost(customerDailySet, expendAnomalySet);
            //初始化毛利
            initGrossProfit(customerDailySet, grossSet);
            //初始化毛利率
            //报表入库
            persistDailyCustomerOrderDaily(customerDailySet);
        } catch (Exception ex) {
            log.error("createDailyReport error day=" + day, ex);
        }
    }

    /**
     * 报表入库
     *
     * @param customerDailySet
     */
    private void persistDailyCustomerOrderDaily(Set<CustomerOrderDaily> customerDailySet) {
        if (!CollectionUtils.isEmpty(customerDailySet)) {
            List<CustomerOrderDaily> customerDailyList = new ArrayList<>(customerDailySet);
            if (!CollectionUtils.isEmpty(customerDailyList)) {
                // 物理删除当日报表数据
                dailyMapperExt.deleteByReportTime(customerDailyList.get(0).getReportTime());
                // 批量插入当日报表数据
                dailyMapperExt.batchInsert(customerDailyList);
            }
        }
    }

    /**
     * 合并客户结果集
     *
     * @param customerIdAndTypeSet
     * @param list
     * @return
     */
    private Set<HashMap<String, Integer>> unionListToDailySet(Set<HashMap<String, Integer>> customerIdAndTypeSet, List<HashMap<String, Object>>... list) {
        if (list != null && list.length > 0) {
            for (List<HashMap<String, Object>> subList : list) {
                if (!CollectionUtils.isEmpty(subList)) {
                    for (HashMap<String, Object> result : subList) {
                        if (!result.isEmpty() && result.get("customerId") != null && result.get("goodsType") != null) {
                            HashMap<String, Integer> idMap = new HashMap<>(2);
                            ShowCustomerDto showCustomerDto = customerClientService.getShowCustomerById(Integer.valueOf(result.get("customerId").toString()));
                            if (showCustomerDto != null) {
                                idMap.put("customerId", Integer.valueOf(result.get("customerId").toString()));
                                idMap.put("goodsType", Integer.valueOf(result.get("goodsType").toString()));
                                customerIdAndTypeSet.add(idMap);
                            }
                        }
                    }
                }
            }
        }
        return customerIdAndTypeSet;
    }

    /**
     * 初始化数据
     *
     * @param customerIdAndTypeSet
     * @param customerDailySet
     * @param day
     */
    private void generateDailyReport(Set<HashMap<String, Integer>> customerIdAndTypeSet, Set<CustomerOrderDaily> customerDailySet, String day) {
        if (!CollectionUtils.isEmpty(customerIdAndTypeSet)) {

            for (HashMap<String, Integer> customerMap : customerIdAndTypeSet) {
                ShowCustomerDto customerDto = customerClientService.getShowCustomerById(customerMap.get("customerId"));
                CustomerOrderDaily customerDaily = new CustomerOrderDaily();
                customerDaily
                        .setReportTime(day)
                        .setCreateTime(new Date())
                        .setCustomerId(customerMap.get("customerId"))
                        .setGoodsType(customerMap.get("goodsType"))
                        .setCustomerName(customerDto == null ? "" : customerDto.getCustomerName())
                        .setGrossProfit(new BigDecimal(0))
                        .setIncomeAnomalyCost(new BigDecimal(0))
                        .setExpendAnomalyCost(new BigDecimal(0))
                        .setTonnage(new BigDecimal(0))
                        .setAmount(0)
                        .setAnomalyWaybillQuantity(0)
                        .setOrderQuantity(0)
                        .setWaybillQuantity(0)
                        .setExpendFreight(new BigDecimal(0))
                        .setIncomeFreight(new BigDecimal(0))
                        .setGrossProfitRate(new BigDecimal(0));
                customerDailySet.add(customerDaily);
            }
        }
    }

    /**
     * 初始化订单数
     *
     * @param customerDailySet
     * @param orderCountSet
     */
    private void initOrderCount(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> orderCountSet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(orderCountSet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : orderCountSet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("orderQuantity") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setOrderQuantity(Integer.valueOf(orderCount.get("orderQuantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化运单总数
     *
     * @param customerDailySet
     * @param waybillCountSet
     */
    private void initWaybillCount(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> waybillCountSet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(waybillCountSet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : waybillCountSet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("waybillQuantity") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setWaybillQuantity(Integer.valueOf(orderCount.get("waybillQuantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化异常单数
     *
     * @param customerDailySet
     * @param anomalyCountSet
     */
    private void initAnomalyCount(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> anomalyCountSet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(anomalyCountSet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : anomalyCountSet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("anomalyWaybillQuantity") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setAnomalyWaybillQuantity(Integer.valueOf(orderCount.get("anomalyWaybillQuantity").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化数量(单位) && 吨位(单位)
     *
     * @param customerDailySet
     * @param amountAndTonnSet
     */
    private void initAmmountAndTonn(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> amountAndTonnSet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(amountAndTonnSet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : amountAndTonnSet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("amount") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setAmount(Integer.valueOf(orderCount.get("amount").toString()));
                            }
                        }
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("tonnage") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setTonnage(new BigDecimal(orderCount.get("tonnage").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化收入-异常费用
     *
     * @param customerDailySet
     * @param incomeAnomalySet
     */
    private void initIncomeAnomalyCost(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> incomeAnomalySet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(incomeAnomalySet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : incomeAnomalySet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("incomeAnomalyCost") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setIncomeAnomalyCost(new BigDecimal(orderCount.get("incomeAnomalyCost").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化成本-异常费用
     *
     * @param customerDailySet
     * @param expendAnomalySet
     */
    private void initExpendAnomalyCost(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> expendAnomalySet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(expendAnomalySet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : expendAnomalySet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("expendAnomalyCost") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setExpendAnomalyCost(new BigDecimal(orderCount.get("expendAnomalyCost").toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 初始化毛利
     *
     * @param customerDailySet
     * @param grossSet
     */
    private void initGrossProfit(Set<CustomerOrderDaily> customerDailySet, List<HashMap<String, Object>> grossSet) {
        if (!CollectionUtils.isEmpty(customerDailySet) && !CollectionUtils.isEmpty(grossSet)) {
            for (CustomerOrderDaily orderDaily : customerDailySet) {
                for (HashMap<String, Object> orderCount : grossSet) {
                    if (!CollectionUtils.isEmpty(orderCount)) {
                        if (orderCount.get("customerId") != null && orderCount.get("goodsType") != null && orderCount.get("grossProfit") != null) {
                            if (orderDaily.getCustomerId().equals(Integer.valueOf(orderCount.get("customerId").toString())) && orderDaily.getGoodsType().equals(Integer.valueOf(orderCount.get("goodsType").toString()))) {
                                orderDaily.setGrossProfit(new BigDecimal(orderCount.get("grossProfit").toString()));
                            }
                        }
                    }
                }
            }
        }
    }


}