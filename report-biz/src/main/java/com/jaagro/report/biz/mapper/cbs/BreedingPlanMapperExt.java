package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.finance.BatchDetailDto;
import com.jaagro.report.api.dto.finance.BreedingPlan;
import com.jaagro.report.api.dto.finance.BreedingPlanDetailsDo;
import com.jaagro.report.api.dto.finance.BreedingPlanInfoCriteria;
import com.jaagro.report.api.dto.finance.BreedingPlanInfoDo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gao
 */
public interface BreedingPlanMapperExt {

    /**
     * 根据客户id查询养殖批次
     * @author gao
     * @param customerId
     * @return
     */
    List<BreedingPlanInfoDo> listBreedingPlanInfoByBatchType(@Param("customerId") Integer customerId);

    /**
     * 根据客户id查询养殖批次
     * @param customerId
     * @return
     */
    List<BreedingPlanInfoDo> listBreedingPlanInfoByPurchaseOrderType(@Param("customerId") Integer customerId);

    /**
     * 根据条件查询
     * @param criteria
     * @return
     */
    List<BreedingPlanDetailsDo> getBreedingPlanInfo(BreedingPlanInfoCriteria criteria);

    /**
     * @param criteria
     * @return
     */
    BreedingPlan getBreedingPlanByCriteria(BreedingPlanInfoCriteria criteria);

    /**
     * 根据批次号查询
     * @param batchNo
     * @return
     */
    BatchDetailDto selectByBatchNo(@Param("batchNo") String batchNo);


}
