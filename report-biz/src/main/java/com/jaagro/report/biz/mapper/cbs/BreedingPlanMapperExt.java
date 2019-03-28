package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.BreedingPlanInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BreedingPlanMapperExt {

    /**
     *
     * @param customerId
     * @return
     */
    List<BreedingPlanInfoDo> listBreedingPlanInfo(@Param("customerId") Integer customerId);
}
