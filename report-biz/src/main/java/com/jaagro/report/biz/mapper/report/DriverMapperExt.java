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

}