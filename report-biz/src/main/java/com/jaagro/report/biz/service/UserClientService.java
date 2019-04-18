package com.jaagro.report.biz.service;

import com.jaagro.report.api.dto.DepartmentReturnDto;
import com.jaagro.utils.BaseResponse;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tony
 */
@FeignClient("user")
public interface UserClientService {

    /**
     * 获取当前token可查询的数据范围 -- 依据部门id
     *
     * @return
     */
    @PostMapping("/getDownDepartment")
    List<Integer> getDownDepartment();

    /**
     * 获取指定部门id及下属部门id数组
     *
     * @return
     */
    @PostMapping("/getDownDepartmentByDeptId/{deptId}")
    List<Integer> getDownDepartmentByDeptId(@PathVariable("deptId") Integer deptId);

    /**
     * 根据部门id查询部门名称
     *
     * @param id
     * @return
     */
    @GetMapping("/getDeptNameById/{id}")
    String getDeptNameById(@PathVariable("id") Integer id);

    @PostMapping("/getAllDepartments")
    List<DepartmentReturnDto> getAllDepartments();

    /**
     * 获取全部大区id
     *
     * @return
     */
    @GetMapping("/listRegionDepartmentIds")
    List<Integer> listRegionDepartmentIds();

    /**
     * 获取指定部门的下属部门
     *
     * @param deptId
     * @return
     */
    @GetMapping("/getDownDeptIdsByDeptId/{deptId}")
    List<Integer> getDownDeptIdsByDeptId(@PathVariable("deptId") Integer deptId);

    /**
     * 根据网点id查询所属大区信息
     * @param networkId
     * @return
     */
    @GetMapping("/getRegionByNetworkId/{networkId}")
    BaseResponse<DepartmentReturnDto> getRegionByNetworkId(@PathVariable("networkId") Integer networkId);
}
