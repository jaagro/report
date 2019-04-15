package com.jaagro.report.biz.mapper.cbs;


import com.jaagro.report.api.dto.finance.ReturnCoopDto;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;


/**
 * CoopMapperExt接口
 *
 * @author :generator
 * @date :2019/2/21
 */
@Resource
public interface CoopMapperExt {
    /**
     * 查询鸡舍相关信息
     *
     * @param coopIds
     * @return
     */
    List<ReturnCoopDto> listByCoopByCoopId(@Param("coopIds") List<Integer> coopIds);

}