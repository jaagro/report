package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.constant.AuditStatus;
import com.jaagro.report.api.constant.CertificateType;
import com.jaagro.report.api.constant.Constants;
import com.jaagro.report.api.dto.customer.*;
import com.jaagro.report.api.dto.finance.*;
import com.jaagro.report.api.dto.plant.PlantDto;
import com.jaagro.report.api.dto.plant.PlantImageDto;
import com.jaagro.report.api.enums.*;
import com.jaagro.report.api.enums.CustomerTypeEnum;
import com.jaagro.report.api.enums.LoanTypeEnum;
import com.jaagro.report.api.enums.PackageUnitEnum;
import com.jaagro.report.api.enums.ProductTypeEnum;
import com.jaagro.report.api.exception.BusinessException;
import com.jaagro.report.api.service.FinanceService;
import com.jaagro.report.biz.mapper.cbs.*;
import com.jaagro.report.biz.service.CurrentUserService;
import com.jaagro.report.biz.service.CustomerClientService;
import com.jaagro.report.biz.service.OssSignUrlClientService;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对接金融接口
 *
 * @author yj
 * @date 2019/3/27 11:05
 */
@Service
@Slf4j
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private CustomerClientService customerClientService;
    @Autowired
    private OssSignUrlClientService ossSignUrlClientService;
    @Autowired
    private PlantMapperExt plantMapper;
    @Autowired
    private BreedingPlanMapperExt breedingPlanMapper;
    @Autowired
    private PurchaseOrderItemsMapperExt purchaseOrderItemsMapper;
    @Autowired
    private PurchaseOrderMapperExt purchaseOrderMapper;
    @Autowired
    private LoanApplyRecordMapperExt loanApplyRecordMapper;
    @Autowired
    private BreedingBatchParameterMapperExt breedingBatchParameterMapper;
    @Autowired
    private BreedingRecordMapperExt breedingRecordMapper;
    @Autowired
    private DeviceValueMapperExt deviceValueMapper;
    @Autowired
    private BreedingBatchDrugMapperExt breedingBatchDrugMapper;


    /**
     * 获取客户基本信息
     *
     * @return
     */
    @Override
    public CustomerBaseInfoDto getCustomerBaseInfo() {
        GetCustomerUserDto customerUser = currentUserService.getCustomerUserById();
        log.info("O CustomerBaseInfoDto customerUser={}", customerUser);
        if (customerUser == null) {
            throw new BusinessException("当前登录用户信息不存在");
        }
        Integer relevanceId = customerUser.getRelevanceId();
        BaseResponse<CustomerReturnDto> response = customerClientService.getCustomerDetail(relevanceId);
        CustomerReturnDto customerReturnDto = response.getData();
        if (customerReturnDto == null) {
            throw new BusinessException("客户不存在");
        }
        CustomerBaseInfoDto dto = new CustomerBaseInfoDto();
        Integer customerType = customerReturnDto.getCustomerType();
        dto.setCustomerId(customerReturnDto.getId())
                .setCustomerName(customerReturnDto.getCustomerName())
                .setCustomerType(CustomerTypeEnum.getDescByCode(customerType))
                .setHeadPortrait(convertToAbstractUrl(Constants.HEAD_PORTRAIT))
                .setIdCardNo(customerReturnDto.getCreditCode())
                .setOperatorCode(Constants.OPERATOR_CODE)
                .setOperatorName(Constants.OPERATOR_NAME)
                .setSource(Constants.SOURCE);
        List<CustomerContactsReturnDto> contactList = customerReturnDto.getCustomerContactsReturnDtos();
        if (!CollectionUtils.isEmpty(contactList)) {
            dto.setPhoneNo(contactList.get(0).getPhone());
        }
        if (customerType != null && customerType.equals(CustomerTypeEnum.PERSON.getCode())) {
            dto.setRealName(customerReturnDto.getCustomerName());
        } else {
            dto.setRealName(contactList.get(0).getCustomerName());
            List<CustomerQualificationReturnDto> qualificationList = customerReturnDto.getQualifications();
            if (!CollectionUtils.isEmpty(qualificationList)) {
                for (CustomerQualificationReturnDto qualificationReturnDto : qualificationList) {
                    if (!AuditStatus.NORMAL_COOPERATION.equals(qualificationReturnDto.getCertificateStatus())) {
                        continue;
                    }
                    if (CertificateType.BUSINESS_LICENSE.equals(qualificationReturnDto.getCertificateType())) {
                        dto.setBusinessLicense(convertToAbstractUrl(qualificationReturnDto.getCertificateImageUrl()));
                    }
                    if (CertificateType.FRONT_ID_CARD.equals(qualificationReturnDto.getCertificateType())) {
                        dto.setIdCardImgPositive(convertToAbstractUrl(qualificationReturnDto.getCertificateImageUrl()));
                    }
                    if (CertificateType.BACK_ID_CARD.equals(qualificationReturnDto.getCertificateType())) {
                        dto.setIdCardImgNegative(convertToAbstractUrl(qualificationReturnDto.getCertificateImageUrl()));
                    }
                }
            }
        }
        List<PlantDto> plantDtoList = plantMapper.listByCustomerId(relevanceId);
        if (!CollectionUtils.isEmpty(plantDtoList)) {
            boolean hasImage = false;
            for (PlantDto plantDto : plantDtoList) {
                List<PlantImageDto> plantImageDtoList = plantDto.getPlantImageDtoList();
                if (!CollectionUtils.isEmpty(plantImageDtoList)) {
                    hasImage = true;
                    dto.setPlantImageUrl(convertToAbstractUrl(plantImageDtoList.get(0).getImageUrl()));
                    dto.setPlantDetailAddress(plantDto.getProvince() + plantDto.getCity() + plantDto.getCounty() + plantDto.getPlantName());
                }
            }
            if (!hasImage) {
                PlantDto plantDto = plantDtoList.get(0);
                dto.setPlantDetailAddress(plantDto.getProvince() + plantDto.getCity() + plantDto.getCounty() + plantDto.getPlantName());
            }
        }
        return dto;
    }

    private String convertToAbstractUrl(String relativeUrl) {
        String[] strArray = {relativeUrl};
        List<URL> urls = ossSignUrlClientService.listSignedUrl(strArray);
        if (!CollectionUtils.isEmpty(urls)) {
            return urls.get(0).toString();
        }
        return null;
    }

    /**
     * 贷款申请选择协议
     * <p>
     * 返回当前客户的养殖计划列表
     *
     * @return
     */
    @Override
    public List<ReturnBreedingPlanInfoDto> listBreedingPlanInfo(String type) {
        GetCustomerUserDto customerUser = currentUserService.getCustomerUserById();
        log.info("O CustomerBaseInfoDto customerUser={}", customerUser);
        if (customerUser == null) {
            throw new BusinessException("当前登录用户信息不存在");
        }
        Integer relevanceId = customerUser.getRelevanceId();
        ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(relevanceId);
        if (showCustomer == null) {
            throw new BusinessException("客户不存在");
        }
        List<BreedingPlanInfoDo> breedingPlanInfoDos = null;
        if (LoanTypeEnum.BATCH.getType().equals(type)) {
            breedingPlanInfoDos = breedingPlanMapper.listBreedingPlanInfoByBatchType(relevanceId);
        }
        if (LoanTypeEnum.PURCHASE_ORDER.getType().equals(type)) {
            breedingPlanInfoDos = breedingPlanMapper.listBreedingPlanInfoByPurchaseOrderType(relevanceId);
        }
        List<ReturnBreedingPlanInfoDto> returnBreedingPlanInfoDtos = new ArrayList<>();
        for (BreedingPlanInfoDo breedingPlan : breedingPlanInfoDos) {
            ReturnBreedingPlanInfoDto returnBreedingPlanInfoDto = new ReturnBreedingPlanInfoDto();
            returnBreedingPlanInfoDto
                    .setBreedingType("活禽")
                    .setOperatorCode(Constants.OPERATOR_CODE)
                    .setOperatorName(Constants.OPERATOR_NAME)
                    .setSource(Constants.SOURCE);
            if (showCustomer.getId() != null) {
                returnBreedingPlanInfoDto.setCustomerId(showCustomer.getId());
            }
            if (showCustomer.getCustomerName() != null) {
                returnBreedingPlanInfoDto.setCustomerName(showCustomer.getCustomerName());
            }
            if (showCustomer.getCustomerType() != null) {
                returnBreedingPlanInfoDto
                        .setCustomerType(CustomerTypeEnum.getDescByCode(showCustomer.getCustomerType()));
            }
            BeanUtils.copyProperties(breedingPlan, returnBreedingPlanInfoDto);
            if (breedingPlan.getBabyChickPrice() != null && breedingPlan.getPlanChickenQuantity() != null) {
                BigDecimal batchAmount = BigDecimal.valueOf(breedingPlan.getPlanChickenQuantity()).multiply(breedingPlan.getBabyChickPrice());
                returnBreedingPlanInfoDto.setBatchAmount(batchAmount);
            }
            if (LoanTypeEnum.PURCHASE_ORDER.getType().equals(type)) {
                List<ReturnPurchaseOrderDto> returnPurchaseOrderDtos = new ArrayList<>();
                List<PurchaseOrder> purchaseOrders = null;
                if (breedingPlan.getId() != null) {
                    purchaseOrders = purchaseOrderMapper.listPurchaseOrderByPlanId(breedingPlan.getId());
                }
                if (purchaseOrders != null) {
                    for (PurchaseOrder purchaseOrder : purchaseOrders) {
                        ReturnPurchaseOrderDto returnPurchaseOrderDto = new ReturnPurchaseOrderDto();
                        BeanUtils.copyProperties(purchaseOrder, returnPurchaseOrderDto);
                        if (purchaseOrder.getProductType() != null) {
                            returnPurchaseOrderDto
                                    .setProductType(ProductTypeEnum.getDescByCode(purchaseOrder.getProductType()));
                        }
                        if (purchaseOrder.getProductType() != null) {
                            if (ProductTypeEnum.SPROUT.getCode() == purchaseOrder.getProductType()) {
                                returnPurchaseOrderDto.setUnit(PackageUnitEnum.PIECE.getDesc());
                            }
                            if (ProductTypeEnum.FEED.getCode() == purchaseOrder.getProductType()) {
                                returnPurchaseOrderDto.setUnit(PackageUnitEnum.TONS.getDesc());
                            }
                            if (ProductTypeEnum.DRUG.getCode() == purchaseOrder.getProductType()
                                    || ProductTypeEnum.VACCINE.getCode() == purchaseOrder.getProductType()) {
                                returnPurchaseOrderDto.setUnit(PackageUnitEnum.BOX.getDesc());
                            }
                        }
                        if (purchaseOrder.getId() != null) {
                            BigDecimal quantity = purchaseOrderItemsMapper.calculateTotalPurchaseOrderQuantity(purchaseOrder.getId());
                            returnPurchaseOrderDto.setQuantity(quantity);
                        }
                        if (purchaseOrder.getCreateTime() != null) {
                            returnPurchaseOrderDto
                                    .setPurchaseTime(purchaseOrder.getCreateTime());
                        }
                        returnPurchaseOrderDtos.add(returnPurchaseOrderDto);
                    }
                    returnBreedingPlanInfoDto
                            .setReturnPurchaseOrderDtos(returnPurchaseOrderDtos);
                }
            }
            returnBreedingPlanInfoDtos.add(returnBreedingPlanInfoDto);
        }
        return returnBreedingPlanInfoDtos;
    }

    /**
     * 贷款申请 选择订单
     * <p>
     * 返回当前客户的养殖计划
     *
     * @return
     */
    @Override
    public List<ReturnBreedingPlanDetailsDto> getBreedingPlanInfo(BreedingPlanInfoCriteria criteria) {
        GetCustomerUserDto customerUser = currentUserService.getCustomerUserById();
        log.info("O CustomerBaseInfoDto customerUser={}", customerUser);
        if (customerUser == null) {
            throw new BusinessException("当前登录用户信息不存在");
        }
        Integer relevanceId = customerUser.getRelevanceId();
        BaseResponse<CustomerReturnDto> response = customerClientService.getCustomerDetail(relevanceId);
        CustomerReturnDto customerReturnDto = response.getData();
        if (customerReturnDto == null) {
            throw new BusinessException("客户不存在");
        }
        List<BreedingPlanDetailsDo> breedingPlanDetailsDos = breedingPlanMapper.getBreedingPlanInfo(criteria);
        List<ReturnBreedingPlanDetailsDto> returnBreedingPlanDetailsDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(breedingPlanDetailsDos)) {
            for (BreedingPlanDetailsDo breedingPlanInfo : breedingPlanDetailsDos) {
                ReturnBreedingPlanDetailsDto returnBreedingPlanDetailsDto = new ReturnBreedingPlanDetailsDto();
                BeanUtils.copyProperties(breedingPlanInfo, returnBreedingPlanDetailsDto);
                returnBreedingPlanDetailsDto
                        .setBreedingType("活禽")
                        .setOperatorCode(Constants.OPERATOR_CODE)
                        .setOperatorName(Constants.OPERATOR_NAME)
                        .setSource(Constants.SOURCE);
                if (customerReturnDto.getId() != null) {
                    returnBreedingPlanDetailsDto.setCustomerId(customerReturnDto.getId());
                }
                if (customerReturnDto.getCustomerName() != null) {
                    returnBreedingPlanDetailsDto.setCustomerName(customerReturnDto.getCustomerName());
                }
                if (customerReturnDto.getCustomerType() != null) {
                    returnBreedingPlanDetailsDto
                            .setCustomerType(CustomerTypeEnum.getDescByCode(customerReturnDto.getCustomerType()));
                }
                if (breedingPlanInfo.getProductType() != null) {
                    if (ProductTypeEnum.SPROUT.getCode() == breedingPlanInfo.getProductType()) {
                        returnBreedingPlanDetailsDto.setSpecification(PackageUnitEnum.PIECE.getDesc());
                    }
                    if (ProductTypeEnum.FEED.getCode() == breedingPlanInfo.getProductType()) {
                        returnBreedingPlanDetailsDto.setSpecification(PackageUnitEnum.TONS.getDesc());
                    }
                    if (ProductTypeEnum.DRUG.getCode() == breedingPlanInfo.getProductType()
                            || ProductTypeEnum.VACCINE.getCode() == breedingPlanInfo.getProductType()) {
                        returnBreedingPlanDetailsDto.setSpecification(PackageUnitEnum.BOX.getDesc());
                    }
                    returnBreedingPlanDetailsDto.setProductType(ProductTypeEnum.getDescByCode(breedingPlanInfo.getProductType()));
                }
                if (breedingPlanInfo.getPurchaseOrderId() != null) {
                    BigDecimal quantity = purchaseOrderItemsMapper.calculateTotalPurchaseOrderQuantity(breedingPlanInfo.getPurchaseOrderId());
                    returnBreedingPlanDetailsDto.setQuantity(quantity);
                }

                returnBreedingPlanDetailsDtos.add(returnBreedingPlanDetailsDto);
            }
        }
        return returnBreedingPlanDetailsDtos;
    }

    /**
     * 贷款记录保存
     *
     * @param dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnCustomerInfoDto saveLoanApplyRecord(CreateLoanApplyRecordDto dto) {
        GetCustomerUserDto customerUser = currentUserService.getCustomerUserById();
        log.info("O CustomerBaseInfoDto customerUser={}", customerUser);
        if (customerUser == null) {
            throw new BusinessException("当前登录用户信息不存在");
        }
        Integer relevanceId = customerUser.getRelevanceId();
        ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(relevanceId);
        if (showCustomer == null) {
            throw new BusinessException("客户不存在");
        }
        BreedingPlanInfoCriteria breedingPlanInfoCriteria = new BreedingPlanInfoCriteria();
        breedingPlanInfoCriteria.setBatchNo(dto.getBatchNo());
        BreedingPlan breedingPlanByCriteria = breedingPlanMapper.getBreedingPlanByCriteria(breedingPlanInfoCriteria);
        if (breedingPlanByCriteria == null) {
            throw new BusinessException("当前养殖批次不存在");
        }
        PurchaseOrder purchaseOrder = null;
        if (LoanTypeEnum.PURCHASE_ORDER.getType().equals(dto.getLoanType())) {
            if (dto.getPurchaseNo() != null) {
                purchaseOrder = purchaseOrderMapper.selectByPurchaseNo(dto.getPurchaseNo());
                if (purchaseOrder == null) {
                    throw new BusinessException("当前采购单不存在");
                }
            } else {
                throw new BusinessException("采购单号不能为空");
            }
        }
        LoanApplyRecord loanApplyRecord = new LoanApplyRecord();
        loanApplyRecord
                .setCustomerId(relevanceId)
                .setLoanType(LoanTypeEnum.getCodeByType(dto.getLoanType()));
        if (breedingPlanByCriteria.getId() != null && breedingPlanByCriteria.getBatchNo() != null) {
            loanApplyRecord
                    .setBatchNo(breedingPlanByCriteria.getBatchNo())
                    .setPlanId(breedingPlanByCriteria.getId());
        }
        if (purchaseOrder != null && purchaseOrder.getId() != null && purchaseOrder.getPurchaseNo() != null) {
            loanApplyRecord
                    .setPurchaseOrderNo(purchaseOrder.getPurchaseNo())
                    .setPurchaseOrderId(purchaseOrder.getId());
        }
        loanApplyRecordMapper.insertSelective(loanApplyRecord);
        ReturnCustomerInfoDto returnCustomerInfoDto = new ReturnCustomerInfoDto();
        if (breedingPlanInfoCriteria.getBatchNo() != null) {
            returnCustomerInfoDto
                    .setBatchNo(breedingPlanInfoCriteria.getBatchNo());
        }
        if (purchaseOrder != null && purchaseOrder.getPurchaseNo() != null) {
            returnCustomerInfoDto
                    .setPurchaseNo(purchaseOrder.getPurchaseNo());
        }
        if (showCustomer.getCustomerName() != null) {
            returnCustomerInfoDto
                    .setCustomerName(showCustomer.getCustomerName())
                    .setId(relevanceId);
        }
        return returnCustomerInfoDto;
    }

    /**
     * 贷款预审-养殖详情
     *
     * @param batchNo
     * @return
     */
    @Override
    public BatchDetailDto getBreedingDetail(String batchNo) {
        GetCustomerUserDto customerUser = currentUserService.getCustomerUserById();
        if (customerUser == null) {
            throw new BusinessException("当前登录用户信息不存在");
        }
        Integer relevanceId = customerUser.getRelevanceId();
        ShowCustomerDto showCustomer = customerClientService.getShowCustomerById(relevanceId);
        if (showCustomer == null) {
            throw new BusinessException("客户不存在");
        }
        BatchDetailDto batchDetailDto = breedingPlanMapper.selectByBatchNo(batchNo);
        if (batchDetailDto == null){
            throw new BusinessException("批次不存在");
        }
        // 初始化默认信息
        generateBatchDetail(batchDetailDto,showCustomer);
        // 设置养殖参数信息
        generateBatchParam(batchDetailDto);
        // 设置药品配置信息
        generateBatchDrug(batchDetailDto);
        return batchDetailDto;
    }

    /**
     * 药品配置
     * @param batchDetailDto
     */
    private void generateBatchDrug(BatchDetailDto batchDetailDto) {
        List<BreedingBatchDrugDto> breedingBatchDrugDtoList = breedingBatchDrugMapper.listByBatchNo(batchDetailDto.getBatchNo());
        if (!CollectionUtils.isEmpty(breedingBatchDrugDtoList)){
            for (BreedingBatchDrugDto drugDto : breedingBatchDrugDtoList){
                if (drugDto.getStopDrugFlag() != null && !drugDto.getStopDrugFlag()){
                    List<FeedMedicineDto> feedMedicineDtoList = breedingRecordMapper.listFeedDrugItems(batchDetailDto.getBatchNo(),drugDto.getProductId(),drugDto.getDayAgeStart(),drugDto.getDayAgeEnd());
                    drugDto.setActualValueList(feedMedicineDtoList);
                }
            }
        }
        batchDetailDto.setBreedingBatchDrugDtoList(breedingBatchDrugDtoList);
    }

    /**
     * 参数配置
     * @param batchDetailDto
     */
    private void generateBatchParam(BatchDetailDto batchDetailDto) {
        String batchNo = batchDetailDto.getBatchNo();
        List<BreedingParamDto> breedingParamDtoList = breedingBatchParameterMapper.listByBatchNo(batchNo);
        if (!CollectionUtils.isEmpty(breedingParamDtoList)){
            for (BreedingParamDto breedingParamDto : breedingParamDtoList){
                // 喂养参数
                if (BreedingStandardParamEnum.DIE.getCode() == breedingParamDto.getParamType()) {
                    Map<String, Object> map = breedingRecordMapper.statisticsByParams(batchNo, BreedingRecordTypeEnum.DEATH_AMOUNT.getCode(), breedingParamDto.getDayAge());
                    if (!CollectionUtils.isEmpty(map)){
                        breedingParamDto.setActualValue((BigDecimal) map.get("totalFeed"));
                        breedingParamDto.setUnit((String) map.get("unit"));
                    }
                }
                if (BreedingStandardParamEnum.FEEDING_WEIGHT.getCode() == breedingParamDto.getParamType()) {
                    Map<String, Object> map = breedingRecordMapper.statisticsByParams(batchNo, BreedingRecordTypeEnum.FEED_FOOD.getCode(), breedingParamDto.getDayAge());
                    if (!CollectionUtils.isEmpty(map)){
                        breedingParamDto.setActualValue((BigDecimal) map.get("totalFeed"));
                        breedingParamDto.setUnit((String) map.get("unit"));
                    }
                }
                if (BreedingStandardParamEnum.FEEDING_FODDER_NUM.getCode() == breedingParamDto.getParamType()) {
                    Map<String, Object> map = breedingRecordMapper.statisticsByParams(batchNo, BreedingRecordTypeEnum.FEED_FOOD.getCode(), breedingParamDto.getDayAge());
                    if (!CollectionUtils.isEmpty(map)){
                        breedingParamDto.setActualValue(new BigDecimal((int) map.get("feedTimes")));
                        breedingParamDto.setUnit("次");
                    }
                }
                // 检测参数
                List<BigDecimal> actualResultList = deviceValueMapper.listByBatchNoAndValueType(batchNo,breedingParamDto.getParamType());
                breedingParamDto.setActualResultList(actualResultList);
            }
        }
        batchDetailDto.setBreedingParamDtoList(breedingParamDtoList);
    }


    /**
     * 基础信息
     * @param batchDetailDto
     * @param showCustomer
     */
    private void generateBatchDetail(BatchDetailDto batchDetailDto,ShowCustomerDto showCustomer) {
        batchDetailDto.setBreedingType(Constants.BREEDING_TYPE)
                .setCustomerId(showCustomer.getId())
                .setCustomerName(showCustomer.getCustomerName())
                .setOperatorCode(Constants.OPERATOR_CODE)
                .setOperatorName(Constants.OPERATOR_NAME);
    }
}
