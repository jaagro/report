package com.jaagro.report.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.constant.GoodsUnit;
import com.jaagro.report.api.dto.customer.ShowCustomerDto;
import com.jaagro.report.api.dto.customer.ShowSiteDto;
import com.jaagro.report.api.dto.settlemanage.*;
import com.jaagro.report.api.dto.truck.ShowTruckDto;
import com.jaagro.report.api.dto.waybill.GetWaybillGoodsDto;
import com.jaagro.report.api.dto.waybill.WaybillTracking;
import com.jaagro.report.api.entity.DriverSettleFeeMonthly;
import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.report.biz.mapper.report.DriverMapperExt;
import com.jaagro.report.biz.mapper.tms.*;
import com.jaagro.report.biz.service.CustomerClientService;
import com.jaagro.report.biz.service.TruckClientService;
import com.jaagro.report.biz.service.UserClientService;
import com.jaagro.utils.BaseResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: 结算管理服务
 * @author: @Gao.
 * @create: 2019-04-11 15:48
 **/
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
    public void litDriverFee(String month) {
        List<ReturnDriverInfoDto> returnDriverInfoDtos = driverMapper.listDriverInfo();
        for (ReturnDriverInfoDto returnDriverInfoDto : returnDriverInfoDtos) {
            DriverSettleFeeMonthly driverSettleFeeMonthly = new DriverSettleFeeMonthly();
            BeanUtils.copyProperties(returnDriverInfoDto, driverSettleFeeMonthly);
            //查询当前客户运单id集合
            List<Integer> waybillIds = driverMapper.listWaybillIdByDriverId(returnDriverInfoDto.getDriverId());
            if (!CollectionUtils.isEmpty(waybillIds)) {
                BigDecimal waybillTruckFee = waybillTruckFeeMapper.accumulativeWaybillTruckFee(waybillIds);
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
}
