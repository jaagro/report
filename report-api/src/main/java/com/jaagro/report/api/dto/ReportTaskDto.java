package com.jaagro.report.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 报表定时任务
 *
 * @author yj
 * @since 2018/11/27
 */
@Data
@Accessors(chain = true)
public class ReportTaskDto implements Serializable {
    private static final long serialVersionUID = 6000309244274937542L;
    /**
     * 1-司机 2-客户  3-订单数据 4-运单费用
     */
    public Integer taskType;
    /**1
     * 1-日报表 2-月报表
     */
    public Integer dateType;

    public ReportTaskDto(Integer taskType, Integer dateType) {
        this.taskType = taskType;

        this.dateType = dateType;
    }
}
