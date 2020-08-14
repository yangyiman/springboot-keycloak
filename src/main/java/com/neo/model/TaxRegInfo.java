package com.neo.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 客户纳税人信息
 * 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxRegInfo implements Serializable {
	private static final long serialVersionUID = -2214366708151348718L;
	
	// 机构编号
	public String oid;
	//抬头
	private String title;
	//税种
	private String taxType;
	//税率
	private String taxRate;
	//纳税人种类
	private String payerType;
	//纳税人编号
	private String payerNo;

	//税号
	private String taxNo;
	//公司名称
	private String taxCompName;
	//注册地址
	private String taxAddress;
	//发票银行账号
	private String taxAccountNo;
	//发票开户银行
	private String taxDepositBank;
	//电话
	private String phone;

	//付款银行账号
	private String payAccountNo;
	//付款银行账号持有人名称
	private String payAccountName;
	//付款开户银行
	private String payDepositBank;
	//收款银行账号
	private String collectAccountNo;
	//收款银行账号持有人名称
	private String collectAccountName;
	//收款开户银行
	private String collectDepositBank;

	private String isDeleted;
	private String createdBy;
	private Date createdAt;
	private String modifiedBy;
	private Date modifiedAt;
}
