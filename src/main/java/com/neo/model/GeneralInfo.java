package com.neo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


/**
 * 客户概况信息
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String oid;
    //注册资本
    private BigDecimal registerCapita;
    //币种
    private String currency;
    //实收资本
    private BigDecimal paidInCapita;
    //经营范围
    private String businessScope;
    //主营业务
    private String mainBusiness;
    //资产总额
    private BigDecimal totalAssets;
    //年销售额
    private BigDecimal annualSales;
    //是否三证
    private String isThreeCards ;
    //企业规模
    private String scale;
    //企业性质
    private String nature;
    //企业类型
    private String companyType;
    //二级类型
    private String secondType;
    //机构简介
    private String introduction;
    //所属行业
    private String industry;
    //是否上市
    private String isIpo;

    private String isDeleted;
    private String createdBy;
    private Date createdAt;
    private String modifiedBy;
    private Date modifiedAt;
}