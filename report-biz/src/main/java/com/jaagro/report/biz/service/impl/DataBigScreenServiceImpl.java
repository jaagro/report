package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.service.DataBigScreenService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.OrderReportMapperExt;
import com.jaagro.report.biz.mapper.tms.WaybillFeeReportMapperExt;
import com.jaagro.report.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private OrderReportMapperExt orderReportMapperExt;


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
        //大区id集合
        List<Integer> departmentIds = userClientService.listRegionDepartmentIds();

        //最终结果
        List<ListWaybillQuarterDto> resultList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(departmentIds)) {
            for (Integer id : departmentIds) {
                ListWaybillQuarterDto quarterDto = new ListWaybillQuarterDto();
                criteriaDto.setDeptIds(userClientService.getDownDepartmentByDeptId(id));
                Long aLong = deptWaybillfeeMonthlyMapper.listQuarterWaybill(criteriaDto);
                if (aLong != null && aLong > 0) {
                    quarterDto
                            .setType(userClientService.getDeptNameById(id))
                            .setValue(aLong);
                    resultList.add(quarterDto);
                }

            }
        }
        if (!CollectionUtils.isEmpty(resultList)) {
            Collections.sort(resultList, Comparator.comparingLong(ListWaybillQuarterDto::getValue));
            Collections.reverse(resultList);
        }
        return resultList;

    }

    /**
     * 大区历史运单汇总
     *
     * @return
     */
    @Override
    public List<ListHistoryWaybillDto> listHistoryWaybill() {
        //大区id集合
        List<Integer> departmentIds = userClientService.listRegionDepartmentIds();

        //最终结果
        List<ListHistoryWaybillDto> waybillDtoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(departmentIds)) {
            for (Integer id : departmentIds) {
                ListWaybillQuarterCriteriaDto criteriaDto = new ListWaybillQuarterCriteriaDto();
                criteriaDto.setDeptIds(userClientService.getDownDepartmentByDeptId(id));
                List<ListHistoryWaybillDto> historyWaybill = deptWaybillfeeMonthlyMapper.listHistoryWaybill(criteriaDto);
                if (!CollectionUtils.isEmpty(historyWaybill)) {
                    for (ListHistoryWaybillDto dto : waybillDtoList) {
                        if (!StringUtils.isEmpty(dto.getDepartmentId())) {
                            dto.setX(userClientService.getDeptNameById(dto.getDepartmentId()));
                        }
                    }
                }
                waybillDtoList.addAll(historyWaybill);
            }
        }
        if (!CollectionUtils.isEmpty(waybillDtoList)) {
            Collections.sort(waybillDtoList, Comparator.comparing(ListHistoryWaybillDto::getY));
            Collections.reverse(waybillDtoList);
        }
        return waybillDtoList;
    }

    /**
     * 项目部历史运单汇总
     *
     * @param productType
     * @return
     */
    @Override
    public List<ListDeptHistoryWaybillDto> listHistoryWaybillByDept(Integer productType) {
        ListWaybillQuarterCriteriaDto criteriaDto = new ListWaybillQuarterCriteriaDto();
        criteriaDto.setGoodsType(productType);
        //大区id集合
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
        //最终结果
        List<ListDeptHistoryWaybillDto> resultList = new ArrayList<>();
        List<ListDeptHistoryWaybillDto> waybillDtoList = deptWaybillfeeMonthlyMapper.listHistoryWaybillByDept(criteriaDto);
        if (!CollectionUtils.isEmpty(waybillDtoList)) {
            for (ListDeptHistoryWaybillDto dto : waybillDtoList) {
                if (!StringUtils.isEmpty(dto.getDepartmentId())) {
                    dto.setX(userClientService.getDeptNameById(dto.getDepartmentId()));
                }
            }
            resultList.addAll(waybillDtoList);
        }
        if (!CollectionUtils.isEmpty(resultList)) {
            Collections.sort(resultList, Comparator.comparingLong(ListDeptHistoryWaybillDto::getY));
            Collections.reverse(resultList);
        }
        return resultList;
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
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 6);
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
                c.set(Calendar.DATE, 1);
            }
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
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


    @Override
    public List<RedBlackBoardDto> listRedBlackBoardData(String boardType) {
        List<RedBlackBoardDto> dtoList;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date yesterday = DateUtils.addDays(today, -1);
        String startDay = format.format(yesterday);
        String endDay = format.format(today);
        if ("1".equals(boardType)) {
            //红榜
            dtoList = orderReportMapperExt.listRedBoardData(startDay, endDay);
        } else {
            //黑榜
            dtoList = orderReportMapperExt.listBlackBoardData(startDay, endDay);
        }
        return dtoList;
    }

    public static void main(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String first = format.format(c.getTime());
        System.out.println("===============first:" + first);

        //获取当前月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(c.getTime());
        System.out.println("===============last:" + last);

    }
}