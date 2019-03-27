package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 季度运单查询dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListWaybillQuarterCriteriaDto implements Serializable {

    private Integer goodsType;

    private List<Integer> deptIds;

    private Date startDate;

    private Date endDate;

}
