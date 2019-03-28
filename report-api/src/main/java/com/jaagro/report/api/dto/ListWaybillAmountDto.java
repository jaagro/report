package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 运单量返回dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListWaybillAmountDto implements Serializable {

    private Integer departmentId;

    private String x;

    private String y;

    private String z;
}
