package com.jaagro.report.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.constant.GoodsUnit;
import com.jaagro.report.api.constant.SettleBillingDayConfigType;
import com.jaagro.report.api.dto.customer.ShowCustomerDto;
import com.jaagro.report.api.dto.customer.ShowSiteDto;
import com.jaagro.report.api.dto.settlemanage.*;
import com.jaagro.report.api.dto.truck.ShowTruckDto;
import com.jaagro.report.api.dto.waybill.GetWaybillGoodsDto;
import com.jaagro.report.api.dto.waybill.WaybillTracking;
import com.jaagro.report.api.entity.DriverSettleFeeMonthly;
import com.jaagro.report.api.entity.CustomerSettleFeeMonthly;
import com.jaagro.report.api.entity.SettleBillingDayConfig;
import com.jaagro.report.api.entity.SettleBillingDayConfigExample;
import com.jaagro.report.api.exception.BusinessException;
import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.report.biz.mapper.report.DriverMapperExt;
import com.jaagro.report.api.util.DateUtil;
import com.jaagro.report.biz.mapper.report.CustomerSettleFeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.report.SettleBillingDayConfigMapperExt;
import com.jaagro.report.biz.mapper.tms.*;
import com.jaagro.report.biz.service.CustomerClientService;
import com.jaagro.report.biz.service.TruckClientService;
import com.jaagro.report.biz.service.UserClientService;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.applet.Main;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description: 结算管理服务
 * @author: @Gao.
 * @create: 2019-04-11 15:48
 **/
@Slf4j
@Service
public class SettleManageServiceImpl implements SettleManageService {

    @Autowired
    private WaybillMapperExt waybillMapper;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private WaybillTrackingMapperExt waybillTrackingMapper;
    @Autowired
    private TruckClientService truckClientService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private WaybillGoodsMapperExt waybillGoodsMapper;
    @Autowired
    private WaybillCustomerFeeMapperExt waybillCustomerFeeMapper;
    @Autowired
    private WaybillTruckFeeMapperExt waybillTruckFeeMapper;
    @Autowired
    private DriverMapperExt driverMapper;
    @Autowired
    private SettleBillingDayConfigMapperExt settleBillingDayConfigMapper;
    @Autowired
    private CustomerSettleFeeMonthlyMapperExt customerSettleFeeMonthlyMapper;

    private static final Integer BILLING_DAY_SEPARATE = 15;
    /**
     * 运单结算费用报表
     *
     * @param criteria
     * @return
     */
    @Override
    public PageInfo listWaybillFee(WaybillFeeCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        if (criteria.getTruckNumber() != null) {
            BaseResponse<List<Integer>> truckIds = truckClientService.listTruckIdsByKeyword(criteria.getTruckNumber());
            if (!CollectionUtils.isEmpty(truckIds.getData())) {
                criteria.setTruckIds(truckIds.getData());
            } else {
                criteria.setTruckIds(Collections.singletonList(999999999));
            }
        }
        if (criteria.getCustomerName() != null) {
            List<Integer> customerIds = listCustomerIdsByKeyword(criteria.getCustomerName());
            criteria.setCustomerIds(customerIds);
        }
        List<ReturnWaybillFeeDto> returnWaybillFeeDtos = waybillMapper.listSettleManageWaybillFee(criteria);
        for (ReturnWaybillFeeDto returnWaybillFeeDto : returnWaybillFeeDtos) {
            //客户名称
            if (returnWaybillFeeDto.getCustomerId() != null) {
                ShowCustomerDto customer = customerClientService.getShowCustomerById(returnWaybillFeeDto.getCustomerId());
                if (customer != null && customer.getCustomerName() != null) {
                    returnWaybillFeeDto.setCustomerName(customer.getCustomerName());
                }
            }
            //获取装货地
            ShowSiteDto showSite = customerClientService.getShowSiteById(returnWaybillFeeDto.getLoadSiteId());
            if (showSite != null && showSite.getSiteName() != null) {
                WaybillTracking loadSiteInfo = waybillTrackingMapper.getLoadSiteInfoByWaybillId(returnWaybillFeeDto.getWaybillId());
                if (loadSiteInfo != null) {
                    returnWaybillFeeDto
                            .setLoadSiteTime(loadSiteInfo.getCreateTime())
                            .setLoadSiteName(showSite.getSiteName());
                }
            }
            //获取卸货地
            if (returnWaybillFeeDto.getWaybillId() != null) {
                WaybillTracking unloadSiteInfo = waybillTrackingMapper.getUnLoadSiteInfoByWaybillId(returnWaybillFeeDto.getWaybillId());
                if (unloadSiteInfo.getTrackingInfo() != null) {
                    String trackingInfo = unloadSiteInfo.getTrackingInfo();
                    int start = trackingInfo.indexOf("【") + 1;
                    int end = trackingInfo.indexOf("】");
                    returnWaybillFeeDto
                            .setUnLoadSiteTime(unloadSiteInfo.getCreateTime())
                            .setUnLoadSiteName(trackingInfo.substring(start, end));
                }
            }
            //获取车牌号
            if (returnWaybillFeeDto.getTruckId() != null) {
                ShowTruckDto truck = truckClientService.getTruckByIdReturnObject(returnWaybillFeeDto.getTruckId());
                if (truck != null && truck.getTruckNumber() != null) {
                    returnWaybillFeeDto.setTruckNumber(truck.getTruckNumber());
                }
            }
            //部门名称
            if (returnWaybillFeeDto.getNetWorkId() != null) {
                String deptName = userClientService.getDeptNameById(returnWaybillFeeDto.getNetWorkId());
                returnWaybillFeeDto.setNetWorkName(deptName);
            }
            //货物信息
            List<GetWaybillGoodsDto> waybillGoodsDtos = waybillGoodsMapper.listGoodsByWaybillId(Collections.singletonList(returnWaybillFeeDto.getWaybillId()));
            ReturnAccumulativeGoodsDto returnAccumulativeGoodsDto = accumulativeGoods(waybillGoodsDtos);
            if (returnAccumulativeGoodsDto != null) {
                returnWaybillFeeDto
                        .setTotalQuantity(returnAccumulativeGoodsDto.getTotalQuantity())
                        .setTotalWeight(returnAccumulativeGoodsDto.getTotalWeight())
                        .setWaybillGoodDtos(returnAccumulativeGoodsDto.getWaybillGoods());
            }
            //客户费用
            BigDecimal waybillCustomerFee = waybillCustomerFeeMapper.accumulativeWaybillCustomerFee(Collections.singletonList(returnWaybillFeeDto.getWaybillId()));
            BigDecimal customerAnomalyFee = waybillCustomerFeeMapper.accumulativeWaybillCustomerAnomalyFee(Collections.singletonList(returnWaybillFeeDto.getWaybillId()));
            returnWaybillFeeDto
                    .setCustomerFee(waybillCustomerFee);
            returnWaybillFeeDto
                    .setAnomalyCustomerFee(customerAnomalyFee.multiply(BigDecimal.valueOf(-1)));
            //运力侧费用
            BigDecimal truckAnomalyFee = waybillTruckFeeMapper.accumulativeWaybillTruckAnomalyFee(Collections.singletonList(returnWaybillFeeDto.getWaybillId()));
            BigDecimal truckFee = waybillTruckFeeMapper.accumulativeWaybillTruckFee(Collections.singletonList(returnWaybillFeeDto.getWaybillId()));
            returnWaybillFeeDto
                    .setWaybillFee(truckFee);
            returnWaybillFeeDto
                    .setAnomalyWaybillFee(truckAnomalyFee.multiply(BigDecimal.valueOf(-1)));
            //毛利
            BigDecimal grossProfit = waybillCustomerFee.add(customerAnomalyFee).subtract(truckAnomalyFee.add(truckAnomalyFee));
            returnWaybillFeeDto.setGrossProfit(grossProfit);
        }
        return new PageInfo(returnWaybillFeeDtos);
    }

    /**
     * 司机费用
     *
     * @param
     * @return
     */
    @Override
    public void createDriverSettleFeeMonthly(String month) {
        //查询所有司机
        List<ReturnDriverInfoDto> returnDriverInfoDtos = driverMapper.listDriverInfo();
        SettleBillingDayConfigExample configExample = new SettleBillingDayConfigExample();
        configExample.createCriteria().andTypeEqualTo(SettleBillingDayConfigType.DRIVER);
        List<SettleBillingDayConfig> settleBillingDayConfigList = settleBillingDayConfigMapper.selectByExample(configExample);
        if (settleBillingDayConfigList.isEmpty()) {
            log.info("there is not settleBillingDayConfig type={}", SettleBillingDayConfigType.CUSTOMER);
            return;
        }
        DriverFeeCriteria driverFeeCriteria = new DriverFeeCriteria();

        for (ReturnDriverInfoDto returnDriverInfoDto : returnDriverInfoDtos) {
            DriverSettleFeeMonthly driverSettleFeeMonthly = new DriverSettleFeeMonthly();
            BeanUtils.copyProperties(returnDriverInfoDto, driverSettleFeeMonthly);
            //查询当前客户运单id集合

            driverFeeCriteria
                    .setDriverId(returnDriverInfoDto.getDriverId());
            List<Integer> waybillIds = driverMapper.listWaybillIdByCriteria(driverFeeCriteria);
            if (!CollectionUtils.isEmpty(waybillIds)) {
                //运单数
                driverSettleFeeMonthly.setTotalWaybill(waybillIds.size());
                //运力费用
                BigDecimal waybillTruckFee = waybillTruckFeeMapper.accumulativeWaybillTruckFee(waybillIds);
                //异常费用
                BigDecimal AnomalyFee = waybillTruckFeeMapper.accumulativeWaybillTruckAnomalyFee(waybillIds);
                driverSettleFeeMonthly
                        .setTotalAnomalyFee(AnomalyFee)
                        .setTotalFreight(waybillTruckFee);
                List<GetWaybillGoodsDto> getWaybillGoodsDtos = waybillGoodsMapper.listGoodsByWaybillId(waybillIds);
                ReturnAccumulativeGoodsDto returnAccumulativeGoodsDto = accumulativeGoods(getWaybillGoodsDtos);
                if (returnAccumulativeGoodsDto != null) {
                    driverSettleFeeMonthly
                            .setTotalWeight(returnAccumulativeGoodsDto.getTotalWeight())
                            .setTotalQuantity(returnAccumulativeGoodsDto.getTotalQuantity());
                }
            }
        }
    }

    /**
     * 累计商品信息
     *
     * @param waybillGoodsDtos
     * @return
     */
    private ReturnAccumulativeGoodsDto accumulativeGoods(List<GetWaybillGoodsDto> waybillGoodsDtos) {
        ReturnAccumulativeGoodsDto returnAccumulativeGoodsDto = new ReturnAccumulativeGoodsDto();
        Integer totalQuantity = 0;
        BigDecimal totalWeight = BigDecimal.ZERO;
        List<WaybillGoodDto> waybillGoods = new ArrayList<>();
        if (!CollectionUtils.isEmpty(waybillGoodsDtos)) {
            for (GetWaybillGoodsDto waybillGoodsDto : waybillGoodsDtos) {
                if (GoodsUnit.TON.equals(waybillGoodsDto.getGoodsUnit())) {
                    totalWeight = totalWeight.add(waybillGoodsDto.getUnloadWeight());
                } else {
                    totalQuantity = totalQuantity + waybillGoodsDto.getUnloadQuantity();
                }
                WaybillGoodDto goods = new WaybillGoodDto();
                BeanUtils.copyProperties(waybillGoodsDto, goods);
                waybillGoods.add(goods);
            }
        }
        returnAccumulativeGoodsDto
                .setTotalWeight(totalWeight)
                .setWaybillGoods(waybillGoods)
                .setTotalQuantity(totalQuantity);
        return returnAccumulativeGoodsDto;
    }

    /**
     * 生成客户结算费用月度报表
     *
     * @param month
     * @author yj
     */
    @Override
    public void createCustomerSettleFeeMonthly(String month) {
        // 查询所有客户
        List<ShowCustomerDto> showCustomerDtoList = customerClientService.listNormalCustomer();
        if (CollectionUtils.isEmpty(showCustomerDtoList)) {
            log.info("there is no normal customer");
            throw new BusinessException("客户列表为空");
        }
        List<CustomerSettleFeeMonthly> customerSettleFeeMonthlyList = new ArrayList<>();
        SettleBillingDayConfigExample configExample = new SettleBillingDayConfigExample();
        configExample.createCriteria().andTypeEqualTo(SettleBillingDayConfigType.CUSTOMER);
        List<SettleBillingDayConfig> settleBillingDayConfigList = settleBillingDayConfigMapper.selectByExample(configExample);
        if (settleBillingDayConfigList.isEmpty()) {
            log.info("there is not settleBillingDayConfig type={}", SettleBillingDayConfigType.CUSTOMER);
            throw new BusinessException("结算账单日未配置");
        }

        ReturnTimeIntervalDto returnTimeIntervalDto = accumulativeTimeInterval(month, SettleBillingDayConfigType.CUSTOMER);
        if (returnTimeIntervalDto.getEnd() == null || returnTimeIntervalDto.getStart() == null){
            return;
        }
        Date start = returnTimeIntervalDto.getStart();
        Date end = returnTimeIntervalDto.getEnd();
        month = returnTimeIntervalDto.getMonth();
        SettleBillingDayConfig config = settleBillingDayConfigList.get(0);
        for (ShowCustomerDto customerDto : showCustomerDtoList) {
            CustomerSettleFeeMonthly settleFeeMonthly = new CustomerSettleFeeMonthly();
            settleFeeMonthly.setCreateTime(new Date())
                    .setCustomerId(customerDto.getId())
                    .setCustomerName(customerDto.getCustomerName())
                    .setCustomerType(customerDto.getCustomerType());
            settleFeeMonthly.setEndTime(end)
                    .setReportTime(month)
                    .setStartTime(start);
            Map<String, Object> map = waybillMapper.selectByParams(customerDto.getId(), start, end);
            if (!CollectionUtils.isEmpty(map)) {
                settleFeeMonthly.setTotalFreight(map.get("total_freight") == null ? new BigDecimal("0") : (BigDecimal) map.get("total_freight"))
                        .setTotalQuantity(map.get("total_quantity") == null ? 0 : ((Long) map.get("total_quantity")).intValue())
                        .setTotalWaybill(map.get("total_waybill") == null ? 0 : ((Long) map.get("total_waybill")).intValue())
                        .setTotalWeight(map.get("total_weight") == null ? new BigDecimal("0") : (BigDecimal) map.get("total_weight"));
            } else {
                settleFeeMonthly.setTotalWeight(new BigDecimal("0"))
                        .setTotalWaybill(0)
                        .setTotalQuantity(0)
                        .setTotalFreight(new BigDecimal("0"));
            }
            customerSettleFeeMonthlyList.add(settleFeeMonthly);
        }
        customerSettleFeeMonthlyMapper.batchInsert(customerSettleFeeMonthlyList);
    }

    /**
     * 查询客户结算费用月度报表
     *
     * @param criteria
     * @return
     */
    @Override
    public PageInfo<CustomerSettleFeeMonthly> listCustomerSettleFeeMonthly(CustomerSettleFeeMonthlyCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(),criteria.getPageSize());
        List<CustomerSettleFeeMonthly> settleFeeMonthlyList =  customerSettleFeeMonthlyMapper.listByCriteria(criteria);
        return new PageInfo<>(settleFeeMonthlyList);
    }

    /**
     * 根据客户名称迷糊查询出客户id集合
     *
     * @param keyword
     * @return
     */
    private List<Integer> listCustomerIdsByKeyword(String keyword) {
        List<Integer> customerIds;
        BaseResponse<List<Integer>> listBaseResponse = customerClientService.listCustomerIdByKeyWord(keyword);
        if (!CollectionUtils.isEmpty(listBaseResponse.getData())) {
            customerIds = listBaseResponse.getData();
        } else {
            customerIds = Collections.singletonList(999999999);
        }
        return customerIds;
    }

    /**
     * 计算账单起始时间与截止时间
     * @param month
     * @param settleBillingDayConfigType
     * @return
     */
    @Override
    public ReturnTimeIntervalDto accumulativeTimeInterval(String month, Integer settleBillingDayConfigType) {
        ReturnTimeIntervalDto returnTimeIntervalDto = new ReturnTimeIntervalDto();
        SettleBillingDayConfigExample configExample = new SettleBillingDayConfigExample();
        configExample.createCriteria().andTypeEqualTo(settleBillingDayConfigType);
        List<SettleBillingDayConfig> settleBillingDayConfigList = settleBillingDayConfigMapper.selectByExample(configExample);
        if (settleBillingDayConfigList.isEmpty()) {
            log.info("there is not settleBillingDayConfig type={}", settleBillingDayConfigType);
            throw new BusinessException("未找到结算账单日配置");
        }
        SettleBillingDayConfig config = settleBillingDayConfigList.get(0);
        Date start = null;
        Date end = null;
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int monthToday = cal.get(Calendar.MONTH)+1;
        int monthInt = Integer.parseInt(month.substring(5,7));
        if (monthInt > monthToday){
            throw new BusinessException("月份不能大于当月");
        }
        String billingDay = config.getBillingDay();
        int billingDayInt = Integer.parseInt(billingDay);
        if (Integer.parseInt(billingDay) < BILLING_DAY_SEPARATE) {
            if (monthToday == monthInt){
                if (day <= billingDayInt){
                    end = DateUtil.truncate(now);
                    start = DateUtils.addMonths(end, -1);
                    month = DateUtil.formatMonth(DateUtils.addMonths(DateUtil.parseMonth(month),-1));
                } else {
                    start = DateUtil.parseDate(month + "-" + billingDay);
                    end = DateUtil.truncate(now);
                }
            }else {
                start = DateUtil.parseDate(month+"-"+billingDay);
                end = DateUtils.addMonths(start,1);
                if (end.after(now)){
                    end = DateUtil.truncate(end);
                }
            }
        } else {
            if (monthToday == monthInt){
                if (day <= billingDayInt){
                    end = DateUtil.truncate(now);
                    start = DateUtils.addMonths(DateUtil.parseDate(month+"-"+billingDay), -1);
                }else {
                    start = DateUtil.parseDate(month+"-"+billingDay);
                    end = DateUtil.truncate(now);
                    month = DateUtil.formatMonth(DateUtils.addMonths(DateUtil.parseMonth(month),1));
                }
            }else {
                end = DateUtil.parseDate(month+"-"+billingDay);
                start = DateUtils.addMonths(end,-1);
            }
        }
        returnTimeIntervalDto
                .setEnd(end)
                .setStart(start)
                .setMonth(month);
        return returnTimeIntervalDto;
    }

    public static void main(String[] args) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
        System.out.println(cal.get(Calendar.MONTH));
        System.out.println("2019-04-03".substring(5,7));
    }
}
