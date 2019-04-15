package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 洗车图片返回dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListWashTruckImageDto implements Serializable {

    /**
     * 描述 车牌号
     */
    private String description;

    /**
     * 路径
     */
    private String url;
}
