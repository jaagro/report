package com.jaagro.report.biz.mapper.report;

import com.jaagro.report.api.dto.settlemanage.DriverFeeCriteria;
import com.jaagro.report.api.dto.settlemanage.ReturnDriverInfoDto;

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
     * @param criteria
     * @return
     */
    List<Integer> listWaybillIdByCriteria(DriverFeeCriteria criteria);

}