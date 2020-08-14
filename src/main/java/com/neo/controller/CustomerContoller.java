package com.neo.controller;

import com.alibaba.fastjson.JSONObject;
import com.neo.config.BaseResult;
import com.neo.model.Customer;
import com.neo.utils.KeycloakTool;
import com.neo.utils.KeyCloakAPIHttpClient;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户
 * Created by 911 on 2020/4/10.
 */

@Api(value = "客户管理", description = "客户API", position = 200, protocols = "http")
@RestController
@RequestMapping(value = "/customer")
public class CustomerContoller {

    private final static Logger logger = LoggerFactory.getLogger(CustomerContoller.class);

    @ApiOperation(value = "客户信息分页查询", notes = "客户信息分页查询")
    @RequestMapping(value = {"/baseinfo"}, method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getCustomerPageBaseInfo(@RequestParam(value = "currentNum", defaultValue = "1") Integer currentNum,
                                        @RequestParam(value = "pagePerNum", defaultValue = "10") Integer pagePerNum,
                                        @RequestParam(value = "filter", defaultValue = "{}") String filter) {
          return  null;
    }



    @ApiOperation(value = "所有客户信息查询", notes = "所有客户信息查询")
    @RequestMapping(value = {"/baseinfo/all"}, method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getAllCustomerInfoes(@RequestParam(required=false) String name) {

        String token = KeycloakTool.getToken();

        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/";
        Map<String,String> param=new HashMap<String,String>();
        try {
            return  KeyCloakAPIHttpClient.sendHttpGet(url,param,token);
        } catch (Exception e) {
            e.printStackTrace();
            return "查询出错:"+e;
        }
    }



    @ApiOperation(value = "新增客户信息", notes = "新增客户信息")
    @RequestMapping(value = {"/baseinfo"}, method = RequestMethod.POST)
    @ResponseBody
    public String addCustomer(@RequestBody(required=true) JSONObject parm, HttpServletRequest request) {
        System.out.println(parm.toJSONString());
        //开始写业务逻辑

        String json = "{\"name\":\""+ parm.getString("name")+"\"}";
        String token = KeycloakTool.getToken();
        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups";
        try {
            //keycloak创建组
            return KeyCloakAPIHttpClient.sendHttpPost(url, json, token);

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "修改客户详细信息", notes = "修改客户详细信息")
    @ResponseBody
    public String getCustomerDetail( @PathVariable("id") String id,@RequestBody(required=true) JSONObject parm ) {
        System.out.println(parm.toJSONString());
        //开始写业务逻辑

        String json = "{\"name\":\""+ parm.getString("name")+"\"}";
        String token = KeycloakTool.getToken();
        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id;
        try {
            //keycloak操作
            return KeyCloakAPIHttpClient.sendHttpPut(url,json,token);

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }

    }


    @ApiOperation(value = "新增客户详细信息", notes = "新增客户详细信息")
    @RequestMapping(value = {"/detail"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<Customer> addCustomerDetail(@RequestBody(required=true) Customer parm) {
        System.out.println(parm);
        //开始写业务逻辑


        return null;
    }


    @ApiOperation(value = "修改客户详细信息", notes = "修改客户详细信息")
    @RequestMapping(value = {"/detail"}, method = RequestMethod.PUT)
    @ResponseBody
    public BaseResult<Customer>  updateCustomerDetail(@ApiIgnore Customer customer, @PathVariable("oid") String oid) {

        return null;
    }



    @ApiOperation(value = "查询客户信息详情", notes = "查询客户信息详情")
    @RequestMapping(value = {"/baseinfo/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Customer>  getCustomerSingle( @PathVariable("id") String id) {


        return null;
    }





    @ApiOperation(value = "修改客户信息", notes = "修改客户信息")
    @RequestMapping(value = "/baseinfo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCustomer( @PathVariable("id") String id,@RequestBody(required=true) JSONObject parm ) {
        System.out.println(parm.toJSONString());
        //开始写业务逻辑

        String json = "{\"name\":\""+ parm.getString("name")+"\"}";
        String token = KeycloakTool.getToken();
        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id;
        try {
            //keycloak操作
            return KeyCloakAPIHttpClient.sendHttpPut(url,json,token);

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }

    }



    @ApiOperation(value = "删除客户信息", notes = "删除客户信息")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.DELETE)
    public String deleteCustomer(@PathVariable("id") String id) {

        //开始写业务逻辑
        String token = KeycloakTool.getToken();
        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id;
        try {
            //keycloak操作
            return KeyCloakAPIHttpClient.sendHttpDelete(url,token);

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    @ApiOperation(value = "查询机构树", notes = "查询机构树")
    @RequestMapping(value = "/tree/{id}", method = RequestMethod.GET)
    @ApiImplicitParam(name = "id", value = "客户ID", required = true, dataType = "String", paramType = "path")
    @ResponseBody
    public String getOrgTree( @PathVariable("id") String id) {

        String token = KeycloakTool.getToken();

        String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id;
        Map<String,String> param=new HashMap<String,String>();
        try {
             return  KeyCloakAPIHttpClient.sendHttpGet(url,param,token);
        } catch (Exception e) {
            e.printStackTrace();
            return "查询出错:"+e;
        }
    }

}
