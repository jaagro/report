package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 历史运单返回dto
 *
 * @author baiyiran
 */
@Accessors(chain = true)
@Data
public class ListDeptHistoryWaybillDto implements Serializable {

    private Integer departmentId;

    private String x;

    private Long y;
}
