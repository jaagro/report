package com.jaagro.report.biz.service.impl;

import com.jaagro.report.api.constant.AuditStatus;
import com.jaagro.report.api.constant.CertificateType;
import com.jaagro.report.api.constant.Constants;
import com.jaagro.report.api.dto.customer.*;
import com.jaagro.report.api.dto.finance.BreedingPlanInfoDo;
import com.jaagro.report.api.dto.finance.CustomerBaseInfoDto;
import com.jaagro.report.api.dto.finance.ReturnBreedingPlanInfoDto;
import com.jaagro.report.api.dto.plant.PlantDto;
import com.jaagro.report.api.dto.plant.PlantImageDto;
import com.jaagro.report.api.enums.CustomerTypeEnum;
import com.jaagro.report.api.exception.BusinessException;
import com.jaagro.report.api.service.FinanceService;
import com.jaagro.report.biz.mapper.cbs.BreedingPlanMapperExt;
import com.jaagro.report.biz.mapper.cbs.PlantMapperExt;
import com.jaagro.report.biz.service.CurrentUserService;
import com.jaagro.report.biz.service.CustomerClientService;
import com.jaagro.report.biz.service.OssSignUrlClientService;
import com.jaagro.utils.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
    public List<ReturnBreedingPlanInfoDto> listBreedingPlanInfo() {
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
        List<ReturnBreedingPlanInfoDto> returnBreedingPlanInfoDtos = new ArrayList<>();
        List<BreedingPlanInfoDo> breedingPlanInfoDos = breedingPlanMapper.listBreedingPlanInfo(relevanceId);
        if (!CollectionUtils.isEmpty(breedingPlanInfoDos)) {
            for (BreedingPlanInfoDo breedingPlanInfoDo : breedingPlanInfoDos) {
                ReturnBreedingPlanInfoDto returnBreedingPlanInfoDto = new ReturnBreedingPlanInfoDto();
                returnBreedingPlanInfoDto
                        .setOperatorCode(Constants.OPERATOR_CODE)
                        .setOperatorName(Constants.OPERATOR_NAME)
                        .setSource(Constants.SOURCE);
                if (customerReturnDto.getId() != null) {
                    returnBreedingPlanInfoDto.setCustomerId(customerReturnDto.getId());
                }
                if (customerReturnDto.getCustomerName() != null) {
                    returnBreedingPlanInfoDto.setCustomerName(customerReturnDto.getCustomerName());
                }
                if (customerReturnDto.getCustomerType() != null) {
                    returnBreedingPlanInfoDto
                            .setCustomerType(CustomerTypeEnum.getDescByCode(customerReturnDto.getCustomerType()));
                }
                BeanUtils.copyProperties(breedingPlanInfoDo, returnBreedingPlanInfoDto);
                if (breedingPlanInfoDo.getBabyChickPrice() != null && breedingPlanInfoDo.getPlanChickenQuantity() != null) {
                    BigDecimal batchAmount = BigDecimal.valueOf(breedingPlanInfoDo.getPlanChickenQuantity()).multiply(breedingPlanInfoDo.getBabyChickPrice());
                    returnBreedingPlanInfoDto.setBatchAmount(batchAmount);
                }
                returnBreedingPlanInfoDtos.add(returnBreedingPlanInfoDto);
            }
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
    public List<ReturnBreedingPlanInfoDto> getBreedingPlanInfo() {
        return null;
    }
}
