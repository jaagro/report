package com.jaagro.report.api.dto.finance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author baiyiran
 * @Date 2019/2/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReturnPlantDto implements Serializable {
    /**
     * 养殖场id
     */
    private Integer id;

    /**
     * 养殖场名称
     */
    private String plantName;

    /**
     * 查询鸡舍信息
     */
    private List<ReturnCoopDto> returnCoopDtos;
}
