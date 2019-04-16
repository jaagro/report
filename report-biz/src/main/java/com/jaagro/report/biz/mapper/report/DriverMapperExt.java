package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.settlemanage.ReturnDriverInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tony
 */
public interface DriverMapperExt {
    /**
     * 查询司机相关信息
     */
    List<ReturnDriverInfoDto> listDriverInfo();

    /**
     * 根据司机查询所有的运单id
     *
     * @param driverId
     * @return
     */
    List<Integer> listWaybillIdByDriverId(@Param("driverId") Integer driverId);

}