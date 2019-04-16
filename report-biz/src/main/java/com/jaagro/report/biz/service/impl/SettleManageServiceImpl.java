package com.jaagro.report.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jaagro.report.api.constant.CostType;
import com.jaagro.report.api.constant.Direction;
import com.jaagro.report.api.constant.GoodsUnit;
import com.jaagro.report.api.dto.customer.ShowCustomerDto;
import com.jaagro.report.api.dto.customer.ShowSiteDto;
import com.jaagro.report.api.dto.settlemanage.ReturnWaybillFeeDto;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.WaybillGoodDto;
import com.jaagro.report.api.dto.truck.ShowTruckDto;
import com.jaagro.report.api.dto.waybill.GetWaybillGoodsDto;
import com.jaagro.report.api.dto.waybill.WaybillTracking;
import com.jaagro.report.api.service.SettleManageService;
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
            if (truckIds != null && truckIds.getData() != null) {
                criteria.setTruckIds(truckIds.getData());
            }
        }
        if (criteria.getCustomerName() != null) {
            BaseResponse<List<Integer>> customerIds = customerClientService.listCustomerIdByKeyWord(criteria.getCustomerName());
            if (customerIds != null && customerIds.getData() != null) {
                criteria.setCustomerIds(customerIds.getData());
            }
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
            Integer totalQuantity = 0;
            BigDecimal totalWeight = BigDecimal.ZERO;
            List<GetWaybillGoodsDto> waybillGoodsDtos = waybillGoodsMapper.listGoodsByWaybillId(returnWaybillFeeDto.getWaybillId());
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
            returnWaybillFeeDto
                    .setTotalQuantity(totalQuantity)
                    .setTotalWeight(totalWeight)
                    .setWaybillGoodDtos(waybillGoods);
            //客户费用
            BigDecimal waybillCustomerFee = waybillCustomerFeeMapper.accumulativeWaybillCustomerFee(returnWaybillFeeDto.getWaybillId());
            BigDecimal customerAnomalyFee = waybillCustomerFeeMapper.accumulativeWaybillCustomerAnomalyFee(returnWaybillFeeDto.getWaybillId());
            returnWaybillFeeDto
                    .setCustomerFee(waybillCustomerFee);
                returnWaybillFeeDto
                        .setAnomalyCustomerFee(customerAnomalyFee.multiply(BigDecimal.valueOf(-1)));
            //运力侧费用
            BigDecimal truckAnomalyFee = waybillTruckFeeMapper.accumulativeWaybillTruckAnomalyFee(returnWaybillFeeDto.getWaybillId());
            BigDecimal truckFee = waybillTruckFeeMapper.accumulativeWaybillTruckFee(returnWaybillFeeDto.getWaybillId());
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
}
