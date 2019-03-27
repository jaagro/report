package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 季度运单返回dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListWaybillQuarterDto implements Serializable {

    private Integer departmentId;

    private String type;

    private Long value;

}
