package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.ContributionTopTenCustomerDto;
import com.jaagro.report.api.dto.ListHistoryWaybillDto;
import com.jaagro.report.api.dto.ListWaybillQuarterCriteriaDto;
import com.jaagro.report.api.dto.ListWaybillQuarterDto;
import com.jaagro.report.api.service.DataBigScreenService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.WaybillFeeReportMapperExt;
import com.jaagro.report.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 */
@Service
@Slf4j
public class DataBigScreenServiceImpl implements DataBigScreenService {

    @Autowired
    private CustomerOrderDailyMapperExt customerOrderDailyMapperExt;
    @Autowired
    private DeptWaybillfeeMonthlyMapperExt deptWaybillfeeMonthlyMapper;
    @Autowired
    private UserClientService userClientService;


    /**
     * 客户贡献前十
     */
    @Override
    public List<ContributionTopTenCustomerDto> listTopTenCustomerData() {

        return customerOrderDailyMapperExt.listTopTenCustomerData();
    }

    /**
     * 通过货物类型查询季度运单
     *
     * @param productType
     */
    @Override
    public List<ListWaybillQuarterDto> listQuarterWaybill(Integer productType) {
        ListWaybillQuarterCriteriaDto criteriaDto = new ListWaybillQuarterCriteriaDto();
        criteriaDto
                .setGoodsType(productType)
                .setStartDate(getCurrentQuarterStartTime())
                .setEndDate(getCurrentQuarterEndTime());
        List<Integer> departmentIds = userClientService.listRegionDepartmentIds();
        List<Integer> deptIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departmentIds)) {
            for (Integer deptId : departmentIds) {
                List<Integer> integerList = userClientService.getDownDepartmentByDeptId(deptId);
                if (!CollectionUtils.isEmpty(integerList)) {
                    deptIds.addAll(integerList);
                }
            }
            if (!CollectionUtils.isEmpty(deptIds)) {
                criteriaDto.setDeptIds(deptIds);
            }
        }
        List<ListWaybillQuarterDto> waybillQuarterDtoList = deptWaybillfeeMonthlyMapper.listQuarterWaybill(criteriaDto);
        if (!CollectionUtils.isEmpty(waybillQuarterDtoList)) {
            for (ListWaybillQuarterDto quarterDto : waybillQuarterDtoList) {
                quarterDto.setType(userClientService.getDeptNameById(quarterDto.getDepartmentId()));
            }

        }
        return waybillQuarterDtoList;

    }

    /**
     * 历史运单汇总
     *
     * @return
     */
    @Override
    public List<ListHistoryWaybillDto> listHistoryWaybill() {
        List<ListHistoryWaybillDto> waybillDtoList = deptWaybillfeeMonthlyMapper.listHistoryWaybill();
        if (!CollectionUtils.isEmpty(waybillDtoList)) {
            for (ListHistoryWaybillDto dto : waybillDtoList) {
                dto.setX(userClientService.getDeptNameById(dto.getDepartmentId()));
            }
        }
        return waybillDtoList;
    }

    /**
     * 当前季度的开始时间，即2012-01-1 00:00:00
     *
     * @return
     */
    public Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 4);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
                c.set(Calendar.DATE, 1);
                now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前季度的结束时间，即2012-03-31 23:59:59
     *
     * @return
     */
    public Date getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
}