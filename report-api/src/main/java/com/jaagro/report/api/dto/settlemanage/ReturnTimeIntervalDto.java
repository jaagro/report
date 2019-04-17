package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 时间间隔
 * @author: @Gao.
 * @create: 2019-04-17 10:32
 **/
@Data
@Accessors(chain = true)
public class ReturnTimeIntervalDto implements Serializable {
    /**
     * 开始时间
     */
    private Date start;
    /**
     * 结束时间
     */
    private Date end;
}
