package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.plant.PlantDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @date 2019/3/27 17:17
 */
public interface PlantMapperExt {
    /**
     * 根据客户id查询养殖场
     *
     * @param customerId
     * @return
     */
    List<PlantDto> listByCustomerId(@Param("customerId") Integer customerId);

    /**
     * 根据养殖场id 查询养殖场信息
     *
     * @param plantId
     * @return
     */
    PlantDto listByPlantId(@Param("plantId") Integer plantId);
}
