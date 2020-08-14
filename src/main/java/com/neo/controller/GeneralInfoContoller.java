package com.neo.controller;

import com.neo.config.BaseResult;
import com.neo.model.Customer;
import com.neo.model.Email;
import com.sun.xml.internal.ws.api.message.Attachment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 客户
 * Created by 911 on 2020/4/10.
 */

@Api(value = "客户概况信息管理", description = "客户概况信息API", position = 200, protocols = "http")
@RestController
@RequestMapping(value = "/customer")
public class GeneralInfoContoller {


    @RequestMapping(value = "/attachmentinfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增附件", notes = "新增附件")
    public void addCustomerAttachment(List<Attachment> param) {

    }

    @RequestMapping(value = "/attachmentinfo/{oid}", method = RequestMethod.GET)
    @ApiOperation(value = "查询附件详情", notes = "查询附件详情")
    public void getCustomerAttachmentSingle( @PathVariable("oid") String oid) {

    }

    @RequestMapping(value = "/attachmentinfo", method = RequestMethod.PUT)
    @ApiOperation(value = "修改附件", notes = "修改附件")
    public void updateCustomerAttachment(List<Attachment> param) {

    }

    @RequestMapping(value = "/attachmentinfo/{oid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除附件", notes = "删除附件")
    public void deleteCustomerAttachment(@PathVariable("oid") String oid) {

    }

    @RequestMapping(value = "/contactinfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增联系信息", notes = "新增联系信息")
    public void addCustomerContactInfo( ) {

    }

    @RequestMapping(value = "/contactinfo/{oid}", method = RequestMethod.GET)
    @ApiOperation(value = "查询联系信息详情", notes = "查询联系信息详情")
    public void getCustomerContactInfoSingle( @PathVariable("oid") String oid) {

    }

    @RequestMapping(value = "/contactinfo/{oid}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改联系信息", notes = "修改联系信息")
    public void updateCustomerContactInfo(@PathVariable("oid") String oid) {

    }

    @RequestMapping(value = "/contactinfo/{oid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除联系信息", notes = "删除联系信息")
    public void deleteCustomerContactInfo() {

    }

    @ApiOperation(value = "新增概要信息", notes = "新增概要信息")
    @RequestMapping(value = "/generalinfo", method = RequestMethod.POST)
    public void addCustomerGeneralInfo() {

    }

    @RequestMapping(value = "/generalinfo/{oid}", method = RequestMethod.GET)
    @ApiOperation(value = "查询概要信息详情", notes = "查询概要信息详情")
    public void getCustomerGeneralInfoSingle(@PathVariable("oid") String oid) {

    }

    @ApiOperation(value = "修改概要信息", notes = "修改概要信息")
    @RequestMapping(value = "/generalinfo/{oid}", method = RequestMethod.PUT)
    public void updateCustomerGeneralInfo(@PathVariable("oid") String oid) {

    }

    @ApiOperation(value = "删除概要信息", notes = "删除概要信息")
    @RequestMapping(value = "/generalinfo/{oid}", method = RequestMethod.DELETE)
    public void deleteCustomerGeneralInfo(@PathVariable("oid") String oid) {

    }

    @RequestMapping(value = "/taxreginfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增财务信息", notes = "新增财务信息")
    public void addCustomerTaxRegInfo() {

    }

    @RequestMapping(value = "/taxreginfo/{oid}", method = RequestMethod.GET)
    @ApiOperation(value = "查询财务信息详情", notes = "查询财务信息详情")
    public void getCustomerTaxRegInfoSingle( @PathVariable("oid") String oid) {


    }

    @RequestMapping(value = "/taxreginfo/{oid}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改财务信息", notes = "修改财务信息")
    public void updateCustomerTaxRegInfo(@PathVariable("oid") String oid) {


    }

    @RequestMapping(value = "/taxreginfo/{oid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除财务信息", notes = "删除财务信息")
    public void deleteCustomerTaxRegInfo(@PathVariable("oid") String oid) {


    }


    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    @ApiOperation(value = "发送邮件", notes = "发送邮件")
    public void sendMailVerificationCode(@ApiIgnore Email email) {

    }


    @RequestMapping(value = "/{oid}/chain", method = RequestMethod.GET)
    @ApiOperation(value = "企业信息上链", notes = "企业信息上链")
    public void customerUpChain(@PathVariable("oid") String oid) {

    }
}
