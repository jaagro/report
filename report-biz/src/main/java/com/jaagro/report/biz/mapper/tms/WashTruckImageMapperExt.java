package com.jaagro.report.biz.mapper.tms;


import com.jaagro.report.api.dto.ListWashTruckImageDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baiyiran
 * @since 2019-03-29
 */
public interface WashTruckImageMapperExt {

    List<ListWashTruckImageDto> listWashTruckImage(@Param("type") String type);
}
