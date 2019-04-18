package com.jaagro.report.api.dto.settlemanage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 累计商品相关信息
 * @author: @Gao.
 * @create: 2019-04-16 18:01
 **/
@Data
@Accessors(chain = true)
public class ReturnAccumulativeGoodsDto implements Serializable {
    /**
     * 总的数量
     */
    private Integer totalQuantity;
    /**
     * 总的重量
     */
    private BigDecimal totalWeight;
    private List<WaybillGoodDto> waybillGoods;
}
