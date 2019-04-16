package com.jaagro.report.web.controller;
import com.jaagro.report.api.dto.settlemanage.WaybillFeeCriteria;
import com.jaagro.report.api.service.SettleManageService;
import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author @Gao.
 */
@Slf4j
@RestController
@Api(description = "结算管理", produces = MediaType.APPLICATION_JSON_VALUE)
public class SettleManageController {
    @Autowired
    private SettleManageService settleManageService;

    @ApiOperation("web查询订单日报表数据")
    @PostMapping("/listWaybillFee")
    public BaseResponse listWaybillFee(@RequestBody WaybillFeeCriteria criteria) {
        if (criteria.getPageNum() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "起始页不能为空");
        }
        if (criteria.getPageSize() == null) {
            return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), "每页条数不能为空");
        }
        return BaseResponse.successInstance(settleManageService.listWaybillFee(criteria));
    }
}
