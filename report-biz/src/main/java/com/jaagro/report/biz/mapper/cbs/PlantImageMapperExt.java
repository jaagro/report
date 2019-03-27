package com.jaagro.report.biz.mapper.cbs;

import com.jaagro.report.api.dto.plant.PlantImageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yj
 * @date 2019/3/27 17:39
 */
public interface PlantImageMapperExt {
    /**
     * 根据养殖场id查询
     * @param plantId
     * @return
     */
    List<PlantImageDto> listByPlantId(@Param("plantId") Integer plantId);
}
