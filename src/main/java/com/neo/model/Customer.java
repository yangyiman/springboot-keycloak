package com.neo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sun.corba.se.pept.transport.ContactInfo;
import com.sun.xml.internal.ws.api.message.Attachment;
import lombok.Data;

/**
 * 客户
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //客户编码
    private String oid;
    //客户名称
    private String name;
    //英文名
    private String nameEn;
    //社会统一信用编码
    private String custCardNo;
    //营业执照编码
    private String custBusinessNo;
    //邮编
    private String postCode;
    //法人身份证号
    private String idCardNo;
    //邮箱
    private String email;
    //手机
    private String phone;
    //联系电话
    private String telephone;
    //传真
    private String fax;
    //单位地址
    private String customerAddress;
    //网络地址
    private String networkAddress;
    //联系人
    private String contactName;
    //机构类型（1.总公司,2.分公司,3.部门）
    private String orgType;
    private String parentOid;
    private String finalOid;
    private String parentOname;
    private String finalOname;
    //企业角色，可能有多个
    private String roles;
    //是否审核（Y/N）
    private String isAudited;
    private String auditMessage;
    private String isDeleted;
    private String createdBy;
    private Date createdAt;
    private String modifiedBy;
    private Date modifiedAt;

    private List<Attachment> attachments;
    private ContactInfo contactInfo;
    private GeneralInfo generalInfo;
    private TaxRegInfo taxRegInfo;
    private List<Customer> children;

	//高级查询参数
	private String screen;
}