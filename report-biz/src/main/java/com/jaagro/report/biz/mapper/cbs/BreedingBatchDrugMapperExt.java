package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.BreedingBatchDrugDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 养殖批次药品配置查询
 * @author yj
 * @date 2019/4/1 21:49
 */
public interface BreedingBatchDrugMapperExt {
    /**
     * 根据批次号查询
     * @param batchNo
     * @return
     */
    List<BreedingBatchDrugDto> listByBatchNo(@Param("batchNo") String batchNo);
}
