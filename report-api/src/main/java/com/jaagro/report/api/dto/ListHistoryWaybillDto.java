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
public class ListHistoryWaybillDto implements Serializable {

    private String x;

    private String y;

    private String s;

}
