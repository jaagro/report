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
import com.jaagro.report.api.entity.*;
import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.report.biz.mapper.report.DriverMapperExt;
import com.jaagro.report.api.util.DateUtil;
import com.jaagro.report.biz.mapper.report.CustomerSettleFeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.report.DriverSettleFeeMonthlyMapperExt;
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
    @Autowired
    private DriverSettleFeeMonthlyMapperExt driverSettleFeeMonthlyMapper;

    /**
     * 运单结算费用报表
     *
     * @param criteria
     * @return
     * @author @Gao.
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
     * 生成司机费用月度报表
     *
     * @param
     * @return
     * @author @Gao.
     */
    @Override
    public void createDriverSettleFeeMonthly(String month) {
        //查询所有司机
        List<ReturnDriverInfoDto> returnDriverInfoDtos = driverMapper.listDriverInfo();
        ReturnTimeIntervalDto returnTimeIntervalDto = accumulativeTimeInterval(month, SettleBillingDayConfigType.DRIVER);
        DriverFeeCriteria driverFeeCriteria = new DriverFeeCriteria();
        driverFeeCriteria
                .setEndDate(returnTimeIntervalDto.getEnd())
                .setBeginDate(returnTimeIntervalDto.getStart());
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

    @Override
    public PageInfo listDriverSettleFeeMonthly(ListDriverFeeCriteria criteria) {
        PageHelper.startPage(criteria.getPageNum(), criteria.getPageSize());
        List<ReturnSettleDriverFeeMonthlyDto> driverSettleFeeMonthlies = driverSettleFeeMonthlyMapper.selectByCriteria(criteria);
        return new PageInfo(driverSettleFeeMonthlies);
    }

    /**
     * 司机结算费用详情
     *
     * @param criteria
     * @return
     */
    @Override
    public PageInfo driverSettleFeeMonthlyDetails(DriverFeeDetailsCriteria criteria) {
        WaybillFeeCriteria waybillFeeCriteria = new WaybillFeeCriteria();
        waybillFeeCriteria
                .setStartDate(criteria.getStartDate())
                .setEndDate(criteria.getEndDate())
                .setDriverId(criteria.getDriverId());
        return listWaybillFee(waybillFeeCriteria);
    }

    /**
     * 累计商品信息
     *
     * @param waybillGoodsDtos
     * @return
     * @author @Gao.
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
            return;
        }
        List<CustomerSettleFeeMonthly> customerSettleFeeMonthlyList = new ArrayList<>();
        SettleBillingDayConfigExample configExample = new SettleBillingDayConfigExample();
        configExample.createCriteria().andTypeEqualTo(SettleBillingDayConfigType.CUSTOMER);
        List<SettleBillingDayConfig> settleBillingDayConfigList = settleBillingDayConfigMapper.selectByExample(configExample);
        if (settleBillingDayConfigList.isEmpty()) {
            log.info("there is not settleBillingDayConfig type={}", SettleBillingDayConfigType.CUSTOMER);
            return;
        }
        SettleBillingDayConfig config = settleBillingDayConfigList.get(0);
        for (ShowCustomerDto customerDto : showCustomerDtoList) {
            CustomerSettleFeeMonthly settleFeeMonthly = new CustomerSettleFeeMonthly();
            settleFeeMonthly.setCreateTime(new Date())
                    .setCustomerId(customerDto.getId())
                    .setCustomerName(customerDto.getCustomerName())
                    .setCustomerType(customerDto.getCustomerType());
            Date start = null;
            Date end = null;
            if (Integer.parseInt(config.getBillingDay()) < 15) {
                start = DateUtil.parse(month + "-" + config.getBillingDay(), "yyyy-MM-dd");
                end = DateUtils.addMonths(start, 1);
            } else {
                end = DateUtil.parse(month + "-" + config.getBillingDay(), "yyyy-MM-dd");
                start = DateUtils.addMonths(end, -1);
            }
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
     *
     * @param month
     * @param settleBillingDayConfigType
     * @return
     */
    private ReturnTimeIntervalDto accumulativeTimeInterval(String month, Integer settleBillingDayConfigType) {
        ReturnTimeIntervalDto returnTimeIntervalDto = new ReturnTimeIntervalDto();
        SettleBillingDayConfigExample configExample = new SettleBillingDayConfigExample();
        configExample.createCriteria().andTypeEqualTo(settleBillingDayConfigType);
        List<SettleBillingDayConfig> settleBillingDayConfigList = settleBillingDayConfigMapper.selectByExample(configExample);
        if (settleBillingDayConfigList.isEmpty()) {
            log.info("there is not settleBillingDayConfig type={}", settleBillingDayConfigType);
            return returnTimeIntervalDto;
        }
        SettleBillingDayConfig config = settleBillingDayConfigList.get(0);
        Date start = null;
        Date end = null;
        if (Integer.parseInt(config.getBillingDay()) < 15) {
            start = DateUtil.parse(month + "-" + config.getBillingDay(), "yyyy-MM-dd");
            end = DateUtils.addMonths(start, 1);
        } else {
            end = DateUtil.parse(month + "-" + config.getBillingDay(), "yyyy-MM-dd");
            start = DateUtils.addMonths(end, -1);
        }
        returnTimeIntervalDto
                .setEnd(end)
                .setStart(start);
        return returnTimeIntervalDto;
    }
}
