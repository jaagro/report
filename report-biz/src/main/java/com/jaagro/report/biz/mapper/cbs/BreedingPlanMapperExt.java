package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.BreedingPlan;
import com.jaagro.report.api.dto.finance.BreedingPlanDetailsDo;
import com.jaagro.report.api.dto.finance.BreedingPlanInfoCriteria;
import com.jaagro.report.api.dto.finance.BreedingPlanInfoDo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BreedingPlanMapperExt {

    /**
     * @param
     * @return
     */
    List<BreedingPlanInfoDo> listBreedingPlanInfoByBatchType(@Param("customerId") Integer customerId);

    /**
     * @param
     * @return
     */
    List<BreedingPlanInfoDo> listBreedingPlanInfoByPurchaseOrderType(@Param("customerId") Integer customerId);

    /**
     * @param criteria
     * @return
     */
    List<BreedingPlanDetailsDo> getBreedingPlanInfo(BreedingPlanInfoCriteria criteria);

    /**
     * @param criteria
     * @return
     */
    BreedingPlan getBreedingPlanByCriteria(BreedingPlanInfoCriteria criteria);
}
