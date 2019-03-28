package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.*;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountCriteria;
import com.jaagro.report.api.dto.bigscreen.ListWaybillCountDto;
import com.jaagro.report.api.dto.bigscreen.ListWaybillTotalDto;
import com.jaagro.report.api.service.DataBigScreenService;
import com.jaagro.report.biz.mapper.report.CustomerOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptOrderMonthlyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.OrderReportMapperExt;
import com.jaagro.report.biz.mapper.tms.WaybillAnomalyMapperExt;
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
    private DeptOrderDailyMapperExt deptOrderDailyMapperExt;
    @Autowired
    private UserClientService userClientService;

    @Autowired
    private OrderReportMapperExt orderReportMapperExt;
    @Autowired
    private WaybillAnomalyMapperExt waybillAnomalyMapperExt;

    @Autowired
    private DeptOrderMonthlyMapperExt deptOrderMontlyMapperExt;

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
                criteriaDto.setDeptIds(userClientService.getDownDeptIdsByDeptId(id));
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
                criteriaDto.setDeptIds(userClientService.getDownDeptIdsByDeptId(id));
                List<ListHistoryWaybillDto> historyWaybill = deptWaybillfeeMonthlyMapper.listHistoryWaybill(criteriaDto);
                if (!CollectionUtils.isEmpty(historyWaybill)) {
                    for (ListHistoryWaybillDto dto : historyWaybill) {
                        dto.setX(userClientService.getDeptNameById(id));
                    }
                    waybillDtoList.addAll(historyWaybill);
                }
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
                List<Integer> integerList = userClientService.getDownDeptIdsByDeptId(deptId);
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
     * 项目部统计运量数
     *
     * @param type
     * @return
     */
    @Override
    public List<ListWaybillAmountDto> listWaybillAmountByDept(Integer type) {
        List<ListWaybillAmountDto> listWaybillAmountDtoList = deptOrderDailyMapperExt.listWaybillAmountByDept(type);
        if (!CollectionUtils.isEmpty(listWaybillAmountDtoList)) {
            for (ListWaybillAmountDto amountDto : listWaybillAmountDtoList) {
                if (!StringUtils.isEmpty(amountDto.getDepartmentId())) {
                    amountDto.setX(userClientService.getDeptNameById(amountDto.getDepartmentId()));
                }
            }
        }
        return listWaybillAmountDtoList;
    }

    /**
     * 当月运单异常情况
     *
     * @return
     */
    @Override
    public List<ListThisMonthWaybillAnomalyDto> listThisMonthWaybillAnomaly() {
        return waybillAnomalyMapperExt.listThisMonthWaybillAnomaly();
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

    /**
     * 当月货物明细统计
     * type:1 统计前5天该货物的总运量
     * type:2 统计前5个月货物的总运量
     *
     * @param productType
     * @param type
     * @return
     */
    @Override
    public List<ListWaybillCountDto> listWaybillCountByProdTypeAndType(String productType, String type) {
        List<ListWaybillCountDto> dtoList = new ArrayList<>();
        if(Integer.parseInt(type)==1) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            Date yesterday = DateUtils.addDays(today, -1);
            String strEndDay = format.format(yesterday);
            Date endDay = DateUtils.addDays(today, -5);
            String strStartDay = format.format(endDay);
            ListWaybillCountCriteria countCriteria = new ListWaybillCountCriteria();
            countCriteria.setProductType(productType)
                    .setStrStartDate(strStartDay)
                    .setStrEndDate(strEndDay);
            dtoList = deptOrderDailyMapperExt.listWaybillCountByProdTypeAndType(countCriteria);


        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date today = new Date();
            Date yesterday = DateUtils.addMonths(today,0);
            String strEndMonth = format.format(yesterday);
            Date endDay = DateUtils.addMonths(today, -4);
            String  strStartMonth= format.format(endDay);
            ListWaybillCountCriteria countCriteria = new ListWaybillCountCriteria();
            countCriteria.setProductType(productType)
                    .setStrStartDate(strStartMonth)
                    .setStrEndDate(strEndMonth);
            dtoList = deptOrderMontlyMapperExt.listWaybillCountByProdTypeAndType(countCriteria);
            for (ListWaybillCountDto listWaybillCountDto : dtoList) {
                listWaybillCountDto.setX(listWaybillCountDto.getX()+"-01 00:00:00");
                
            }

        }


        return dtoList;
    }

    /**
     * 数据大屏运量总和
     *
     * @param productType
     * @param type
     * @return
     */
    @Override
    public List<ListWaybillTotalDto> listWaybillTotalByProdTypeAndType(String productType, String type) {
        List<ListWaybillTotalDto> dtoList = new ArrayList<>();
        if(Integer.parseInt(type)==1) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            Date yesterday = DateUtils.addDays(today, -1);
            String strEndDay = format.format(yesterday);
            ListWaybillCountCriteria countCriteria = new ListWaybillCountCriteria();
            countCriteria.setProductType(productType)
                    .setStrEndDate(strEndDay);
            dtoList = deptOrderDailyMapperExt.listWaybillTotalByProdTypeAndType(countCriteria);
        }else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            Date today = new Date();
            Date thisMonth = DateUtils.addMonths(today,0);
            String strThisMonth = format.format(thisMonth);
            ListWaybillCountCriteria countCriteria = new ListWaybillCountCriteria();
            countCriteria.setProductType(productType)
                    .setStrEndDate(strThisMonth);
            dtoList = deptOrderMontlyMapperExt.listWaybillTotalByProdTypeAndType(countCriteria);

        }

        return dtoList;
    }

    /**
     * 数据大屏货物同比
     *
     * @param productType
     * @param type
     * @return
     */
    @Override
    public List<ListWaybillTotalDto> listTotalCompareByProdTypeAndType(String productType, String type) {
        return null;
    }

    public static void main(String[] args) {

        List<RedBlackBoardDto> dtoList;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date today = new Date();
        Date yesterday = DateUtils.addMonths(today,0);
        String strEndMonth = format.format(yesterday);
        Date endDay = DateUtils.addMonths(today, -4);
        String  strStartMonth= format.format(endDay);
        System.out.println("===============startDay:" + strStartMonth);
        System.out.println("===============endDay:" + strEndMonth);

    }
}