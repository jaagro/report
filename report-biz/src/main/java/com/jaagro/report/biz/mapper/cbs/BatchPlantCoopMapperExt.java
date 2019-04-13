package com.jaagro.report.biz.mapper.cbs;


import com.jaagro.report.api.dto.finance.ReturnPlantDto;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * BatchPlantCoopMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface BatchPlantCoopMapperExt {

    /**
     * 查询当前养殖场对应的鸡舍
     *
     * @param plantId
     * @return
     */
    List<Integer> listCoopIdPlantId(@Param("plantId") Integer plantId);

    /**
     * 查询当前批次养殖场信息
     *
     * @param planId
     * @return
     */
    List<ReturnPlantDto> listPlantPlanId(@Param("planId") Integer planId);
}