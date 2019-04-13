package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.BreedingParamDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 养殖计划参数查询
 * @author yj
 * @date 2019/4/1 9:23
 */
public interface BreedingBatchParameterMapperExt {
    /**
     * 根据批次号查询
     * @param batchNo
     * @return
     */
    List<BreedingParamDto> listByBatchNo(@Param("batchNo") String batchNo);
}
