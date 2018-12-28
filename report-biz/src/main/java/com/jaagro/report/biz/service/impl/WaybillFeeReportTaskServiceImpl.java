package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.dto.DepartmentReturnDto;
import com.jaagro.report.api.dto.WaybillFeeReportDto;
import com.jaagro.report.api.entity.DeptWaybillfeeDaily;
import com.jaagro.report.api.entity.DeptWaybillfeeMonthly;
import com.jaagro.report.api.service.WaybillFeeReportTaskService;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeDailyMapperExt;
import com.jaagro.report.biz.mapper.report.DeptWaybillfeeMonthlyMapperExt;
import com.jaagro.report.biz.mapper.tms.WaybillFeeReportMapperExt;
import com.jaagro.report.biz.service.UserClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 运单费用 日 月报表统计服务
 *
 * @author @Gao.
 */
@Service
@Slf4j
public class WaybillFeeReportTaskServiceImpl implements WaybillFeeReportTaskService {

    @Autowired
    private WaybillFeeReportMapperExt waybillFeeReportMapper;
    @Autowired
    private DeptWaybillfeeDailyMapperExt deptWaybillfeeDailyMapper;
    @Autowired
    private DeptWaybillfeeMonthlyMapperExt deptWaybillfeeMonthlyMapper;
    @Autowired
    private UserClientService userClientService;


    /**
     * 生成日报表
     *
     * @param day yyyy-MM-dd
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createDailyReport(String day) {
        List<DeptWaybillfeeDaily> deptWaybillfeeDailies = waybillFeeReportMapper.listWaybillFeeStatisticsByDay(day);
        if (!CollectionUtils.isEmpty(deptWaybillfeeDailies)) {
            //删除当天报表
            deptWaybillfeeDailyMapper.batchDeleteWaybillFeeDailyByDay(day);
            //插入最新日报表
            deptWaybillfeeDailyMapper.batchWaybillFeeDailyInsert(deptWaybillfeeDailies);
        }
    }

    /**
     * 生成月报表
     *
     * @param month yyyy-MM
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMonthlyReport(String month) {
        List<DeptWaybillfeeMonthly> deptWaybillfeeMonthlies = deptWaybillfeeDailyMapper.listWaybillFeeStatisticsByMonth(month);
        if (!CollectionUtils.isEmpty(deptWaybillfeeMonthlies)) {
            //删除当月报表
            deptWaybillfeeMonthlyMapper.batchDeleteWaybillFeeDailyByMonth(month);
            //插入最新月报表
            deptWaybillfeeMonthlyMapper.batchWaybillFeeMonthInsert(deptWaybillfeeMonthlies);
        }
    }

    /**
     * 运单费用日报表列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<DeptWaybillfeeDaily> listWaybillFeeDailyReport(WaybillFeeReportDto dto) {
        if (null != dto.getDeptId()) {
            List<Integer> deptIds = userClientService.getDownDepartmentByDeptId(dto.getDeptId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                dto.setDepartIds(deptIds);
            }
        }
        List<DeptWaybillfeeDaily> deptWaybillfeeDailies = deptWaybillfeeDailyMapper.listWaybillFeeDailyReport(dto);
        if (!CollectionUtils.isEmpty(deptWaybillfeeDailies)) {
            List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
            for (DeptWaybillfeeDaily deptWaybillfeeDaily : deptWaybillfeeDailies) {
                if (null == deptWaybillfeeDaily.getDepartmentId()) {
                    deptWaybillfeeDaily.setDepartmentName("其它");
                } else {
                    DepartmentReturnDto departmentReturnDto = departmentReturnDtos.stream().filter(c -> c.getId().equals(deptWaybillfeeDaily.getDepartmentId())).collect(Collectors.toList()).get(0);
                    if (null != departmentReturnDto) {
                        deptWaybillfeeDaily.setDepartmentName(departmentReturnDto.getDepartmentName());
                    }
                }
            }
        }
        return deptWaybillfeeDailies;
    }

    /**
     * 运单费用月报表列表
     *
     * @param dto
     * @return
     */
    @Override
    public List<DeptWaybillfeeMonthly> listWaybillFeeMonthReport(WaybillFeeReportDto dto) {
        if (null != dto.getDeptId()) {
            List<Integer> deptIds = userClientService.getDownDepartmentByDeptId(dto.getDeptId());
            if (!CollectionUtils.isEmpty(deptIds)) {
                dto.setDepartIds(deptIds);
            }
        }
        List<DeptWaybillfeeMonthly> deptWaybillfeeMonthlies = deptWaybillfeeMonthlyMapper.listWaybillFeeMonthReport(dto);
        if (!CollectionUtils.isEmpty(deptWaybillfeeMonthlies)) {
            List<DepartmentReturnDto> departmentReturnDtos = userClientService.getAllDepartments();
            for (DeptWaybillfeeMonthly deptWaybillfeeMonthly : deptWaybillfeeMonthlies) {
                if (null != deptWaybillfeeMonthly.getDepartmentId()) {
                    DepartmentReturnDto departmentReturnDto = departmentReturnDtos.stream().filter(c -> c.getId().equals(deptWaybillfeeMonthly.getDepartmentId())).collect(Collectors.toList()).get(0);
                    if (null != departmentReturnDto) {
                        deptWaybillfeeMonthly.setDepartmentName(departmentReturnDto.getDepartmentName());
                    }
                } else {
                    deptWaybillfeeMonthly.setDepartmentName("其它");
                }

            }
        }
        return deptWaybillfeeMonthlies;
    }
}
