package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 司机费用
 * @author: @Gao.
 * @create: 2019-04-16 14:31
 **/
@Data
@Accessors(chain = true)
public class DriverFeeCriteria implements Serializable {
    /**
     * 起始页
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;
}
