package com.jaagro.report.api.dto.finance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 养殖参数
 * @author yj
 * @date 2019/3/30 11:29
 */
@Data
@Accessors(chain = true)
public class BreedingParamDto implements Serializable{
    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 日龄
     */
    private Integer dayAge;

    /**
     * 参数类型（10-温度,11-湿度,12-光照强度,13-光照时间,14-氮气,15-二氧化碳,16-通风,17-负压值,20-喂料(次/日),21-喂水(次/日),22-饲喂(克/日),23-体重(克/只/日),24-死淘(只),25-药品/疫苗)
     */
    private Integer paramType;

    /**
     * 数值类型(1-区间值,2-标准值,3-临界值)
     */
    private Integer valueType;

    /**
     * 参考值下限（标准值时有效）
     */
    private BigDecimal lowerLimit;

    /**
     * 参考值上限(临界值时有效)
     */
    private BigDecimal upperLimit;

    /**
     * 单位(摄氏度℃｜百分比%｜百万分比ppm｜立方英尺每分钟cfm｜小时H｜勒克斯lux｜只,｜次/日｜克/只/日)
     */
    private String unit;

    /**
     * 参数值(标准值,临界值)
     */
    private String paramValue;

    /**
     * 临界值方向(1->=,2<=)
     */
    private Integer thresholdDirection;

    /**
     * 仪表参数实际检查结果
     */
    private List<BigDecimal> actualResultList;

    /**
     * 养殖过程中记录的值
     */
    private BigDecimal actualValue;

}
