package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.DepartmentReturnDto;
import com.jaagro.report.api.dto.OrderReportDto;
import com.jaagro.report.api.entity.DeptOrderDaily;
import com.jaagro.report.api.entity.DeptOrderMonthly;
import com.jaagro.report.api.service.OrderReportService;
import com.jaagro.report.biz.mapper.report.DeptOrderDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptOrderMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.OrderReportMapperExt;
import com.jaagro.report.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单报表实现类
 *
 * @author gavin
 * @since 2018/11/28
 */
@Service
@Slf4j
//@CacheConfig(keyGenerator = "wiselyKeyGenerator", cacheNames = "orderReport")
public class OrderReportServiceImpl implements OrderReportService {

    @Autowired
    private UserClientService userClientService;

    @Autowired
    private DeptOrderDailyMapperExt deptOrderDailyMapperExt;

    @Autowired
    private DeptOrderMonthlyMapperExt deptOrderMonthlyMapperExt;

    @Autowired
    private OrderReportMapperExt orderReportMapperExt;

    /**
     * 生成日报表数据
     *
     * @param orderReportDto
     */

    @Override
//    @CacheEvict(cacheNames = "orderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createDailyReport(OrderReportDto orderReportDto) {
        log.info("O OrderReportServiceImpl.createDailyReport orderReportDto:{}", orderReportDto);
        String day = orderReportDto.getReportTime();
        List<DeptOrderDaily> deptOrderDailyList = new ArrayList<>();
        //1、从tms查询数据 day = '20181128'   yyyyMMdd
        deptOrderDailyList = orderReportMapperExt.getDeptOrderDailyDataListFromTms(orderReportDto);
        if (!CollectionUtils.isEmpty(deptOrderDailyList)) {
            //2、删除当天的数据
            deptOrderDailyMapperExt.batchDelete(day);
            //3、把数据保存到report表
            deptOrderDailyMapperExt.batchInsert(deptOrderDailyList);
        }
    }

    /**
     * 异步生成日报表数据
     *
     * @param orderReportDto
     */
    @Override
    @Async
//    @CacheEvict(cacheNames = "orderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createDailyReportAsync(OrderReportDto orderReportDto) {
        String day = orderReportDto.getReportTime();
        List<DeptOrderDaily> deptOrderDailyList = new ArrayList<>();
        //1、从tms查询数据 day = '20181128'   yyyyMMdd
        deptOrderDailyList = orderReportMapperExt.getDeptOrderDailyDataListFromTms(orderReportDto);
        if (!CollectionUtils.isEmpty(deptOrderDailyList)) {
            //2、删除当天的数据
            deptOrderDailyMapperExt.batchDelete(day);
            //3、把数据保存到report表
            deptOrderDailyMapperExt.batchInsert(deptOrderDailyList);
        }
        log.info("O OrderReportServiceImpl.createDailyReportAsync-day={}", orderReportDto);
    }

    /**
     * 生成月报表数据
     *
     * @param orderReportDto yyyyMM
     */

    @Override
//    @CacheEvict(cacheNames = "orderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createMonthlyReport(OrderReportDto orderReportDto) {
        String month = orderReportDto.getReportTime();
        List<DeptOrderMonthly> deptOrderMonthlyList = new ArrayList<>();
        //1、从日报表查询出月报表的数据 day = '201811' yyyyMM
        deptOrderMonthlyList = deptOrderDailyMapperExt.getOrderMonthlyDataFromOrderDaily(orderReportDto);
        if (!CollectionUtils.isEmpty(deptOrderMonthlyList)) {
            //2、删除当月的数据
            deptOrderMonthlyMapperExt.batchDelete(month);
            //3、把数据保存到report表
            deptOrderMonthlyMapperExt.batchInsert(deptOrderMonthlyList);
        }
        log.info("O OrderReportServiceImpl.createMonthlyReport-day={}", orderReportDto);
    }

    /**
     * 异步生成月报表数据
     *
     * @param orderReportDto yyyyMM
     */

    @Override
    @Async
//    @CacheEvict(cacheNames = "orderReport", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void createMonthlyReportAsync(OrderReportDto orderReportDto) {
        String month = orderReportDto.getReportTime();
        List<DeptOrderMonthly> deptOrderMonthlyList = new ArrayList<>();
        //1、从日报表查询出月报表的数据 day = '201811' yyyyMM
        deptOrderMonthlyList = deptOrderDailyMapperExt.getOrderMonthlyDataFromOrderDaily(orderReportDto);
        if (!CollectionUtils.isEmpty(deptOrderMonthlyList)) {
            //2、删除当月的数据
            deptOrderMonthlyMapperExt.batchDelete(month);
            //3、把数据保存到report表
            deptOrderMonthlyMapperExt.batchInsert(deptOrderMonthlyList);
        }
        log.info("O OrderReportServiceImpl.createMonthlyReportAsync-day={}", orderReportDto);
    }

    @Override
    public List<DeptOrderDaily> getDeptOrderDailyDataListFromTms(OrderReportDto orderReportDto) {
        log.info("S OrderReportServiceImpl.getDeptOrderDailyDataListFromTms orderReportDto:{}", orderReportDto);
        List<DeptOrderDaily> orderDailyList = orderReportMapperExt.getDeptOrderDailyDataListFromTms(orderReportDto);
        return orderDailyList;
    }

    /**
     * 从日报表中获取月报表的数据
     *
     * @param orderReportDto
     * @return
     */
    @Override
    public List<DeptOrderMonthly> getOrderMonthlyDataFromOrderDaily(OrderReportDto orderReportDto) {
        log.info("S OrderReportServiceImpl.getOrderMonthlyDataFromOrderDaily orderReportDto:{}", orderReportDto);
        return deptOrderDailyMapperExt.getOrderMonthlyDataFromOrderDaily(orderReportDto);
    }


    @Override
//    @Cacheable
    public List<DeptOrderDaily> listOrderDailyReport(OrderReportDto dto) {
        log.info("S OrderReportServiceImpl.listOrderDailyReport dto:{}", dto);
        if (null != dto.getDeptId()) {
            List<Integer> deptIds = userClientService.getDownDepartmentByDeptId(dto.getDeptId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                dto.setDepartIds(deptIds);
            }
        }
        List<DeptOrderDaily> orderDailyList = deptOrderDailyMapperExt.listOrderDailyReport(dto);
        if (!CollectionUtils.isEmpty(orderDailyList)) {
            List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
            for (DeptOrderDaily deptOrderDaily : orderDailyList) {
                if (StringUtils.isEmpty(deptOrderDaily.getDepartmentId())) {
                    deptOrderDaily.setDepartmentName("其它");
                } else {
                    List<DepartmentReturnDto> departmentReturnDtoList = departmentReturnDtos.stream().filter(c -> c.getId().equals(deptOrderDaily.getDepartmentId())).collect(Collectors.toList());

                    if (!CollectionUtils.isEmpty(departmentReturnDtoList)) {
                        DepartmentReturnDto departmentReturnDto = departmentReturnDtoList.get(0);
                        if(StringUtils.isEmpty(departmentReturnDto.getDepartmentName()))
                        {
                            deptOrderDaily.setDepartmentName("其它");
                        }else {
                            deptOrderDaily.setDepartmentName(departmentReturnDto.getDepartmentName());
                        }
                    }
                }
            }
        }
        return orderDailyList;
    }


    @Override
//    @Cacheable
    public List<DeptOrderMonthly> listOrderMonthlyReport(OrderReportDto dto) {
        log.info("S OrderReportServiceImpl.listOrderMonthlyReport dto:{}", dto);
        if (null != dto.getDeptId()) {
            List<Integer> deptIds = userClientService.getDownDepartmentByDeptId(dto.getDeptId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                dto.setDepartIds(deptIds);
            }
        }
        List<DeptOrderMonthly> orderMonthlyList = deptOrderMonthlyMapperExt.listOrderMonthlyReport(dto);
        if (!CollectionUtils.isEmpty(orderMonthlyList)) {
            List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
            for (DeptOrderMonthly deptOrderMonthly : orderMonthlyList) {
                if (StringUtils.isEmpty(deptOrderMonthly.getDepartmentId())) {
                    deptOrderMonthly.setDepartmentName("其它");
                } else {
                    List<DepartmentReturnDto> departmentDtoList = departmentReturnDtos.stream().filter(c -> c.getId().equals(deptOrderMonthly.getDepartmentId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(departmentDtoList)) {
                        DepartmentReturnDto departmentDto = departmentDtoList.get(0);
                        if (StringUtils.isEmpty(departmentDto.getDepartmentName())) {
                            deptOrderMonthly.setDepartmentName("其它");
                        } else {
                            deptOrderMonthly.setDepartmentName(departmentDto.getDepartmentName());
                        }
                    }
                }
            }
        }
        return orderMonthlyList;
    }
}
