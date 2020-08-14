package com.neo.controller;

import com.neo.model.KeycloakGroup;
import com.neo.utils.KeycloakTool;
import com.neo.model.User;
import io.swagger.annotations.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Api(value = "KeyCloak管理", description = "KeyCloakAPI", position = 100, protocols = "http")
@RestController
@RequestMapping(value = "/keycloak")
public class KeyCloakGroupController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());
    private final static Logger logger = LoggerFactory.getLogger(KeyCloakGroupController.class);

    @ApiOperation(value = "获取组列表", notes = "查询组列表")
    @RequestMapping(value = {"/groups"}, method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组名", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getGroupList(@ApiIgnore KeycloakGroup group) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送get请求
            String url = "http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups";

            if(group.getName()!=null){
                url=url+"?search="+group.getName();
            }

            HttpGet request = new HttpGet(url);

            request.setHeader("Authorization", "bearer " + KeycloakTool.getToken());
            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");


            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                return strResult;
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }

        return "查询出错";
    }


    @ApiOperation(value = "获取组数", notes = "查询组数")
    @RequestMapping(value = {"/groupcount"}, method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getGroupCount() {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送get请求
            String url = "http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/count";
            HttpGet request = new HttpGet(url);

            request.setHeader("Authorization", "bearer " + KeycloakTool.getToken());

            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");


            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                return strResult;
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }

        return "查询出错";
    }

    @ApiOperation(value = "ID获取组", notes = "ID查询组")
    @ApiImplicitParam(name = "id", value = "组ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupById(@PathVariable String id) {

        // 调试日志
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送get请求
            String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id;
            HttpGet request = new HttpGet(url);

            request.setHeader("Authorization", "bearer "+KeycloakTool.getToken());
            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");


            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);

                return strResult;
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }

        return "查询出错";
    }

    @ApiOperation(value = "更新组信息", notes = "根据ID更新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组名", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
    public String putGroup(@PathVariable String id, @ApiIgnore KeycloakGroup group) {

        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送put请求
            String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id+"";
            HttpPut put = new HttpPut(url);

            put.setHeader("Authorization", "bearer "+KeycloakTool.getToken());
            put.setHeader("Content-type", "application/json");
            put.setHeader("Accept", "application/json");

            String json = "{\"name\":\""+group.getName()+"\"}";
            HttpClient httpClient = new DefaultHttpClient();

            StringEntity postingString = new StringEntity(json,"UTF-8");// json传递
            put.setEntity(postingString);

            HttpResponse response = client.execute(put);

            System.out.println(response);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                /**读取服务器返回过来的json字符串数据**/
               /* HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);*/
                return "";
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }
        return "更新出错";

    }

    @ApiOperation(value = "ID删除组", notes = "ID删除组")
    @ApiImplicitParam(name = "id", value = "组ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteGroup(@PathVariable String id) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送put请求
            String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id+"";
            HttpDelete delete = new HttpDelete(url);

            delete.setHeader("Authorization", "bearer "+KeycloakTool.getToken());
            delete.setHeader("Content-type", "application/json");
            delete.setHeader("Accept", "application/json");

            HttpResponse response = client.execute(delete);

            System.out.println(response);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                /**读取服务器返回过来的json字符串数据**/
               /* HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);*/
                return "";
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }
        return "删除出错";

    }

    @ApiOperation(value = "ID获取组员", notes = "ID获取组授权用户")
    @ApiImplicitParam(name = "id", value = "组ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/group/{id}/members", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupembersById(@PathVariable String id) {

        // 调试日志
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送get请求
            String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id+"/members";
            HttpGet request = new HttpGet(url);

            request.setHeader("Authorization", "bearer "+KeycloakTool.getToken());
            request.setHeader("Content-type", "application/json");
            request.setHeader("Accept", "application/json");


            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                return strResult;
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }
        return "查询出错";
    }

    @ApiOperation(value = "创建根目录组", notes = "根据keyCloakAPI创建组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组名", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    @ResponseBody
    public String postGroup(@ApiIgnore KeycloakGroup group) {
        try {

            Keycloak keycloak = Keycloak.getInstance("http://localhost:8180/auth",// keycloak地址
                    "master",// 指定 Realm master
                    "admin",// 管理员账号
                    "admin",// 管理员密码
                    // 指定client（admin-cli是Master Realm中的内置client,Direct Access
                    // Grants Enabled）
                    "admin-cli");
            // 进入 testRealmName01
            RealmResource realmResource = keycloak.realm("quickstart-vanilla");

            GroupRepresentation groupRepresentation = new GroupRepresentation();


            groupRepresentation.setName(group.getName());
            realmResource.groups().add(groupRepresentation);

            return  "成功";
        } catch (Exception e) {
            logger.error("get request:", e);
        }

        return "创建出错";
    }


    @ApiOperation(value = "指定上级组创建当前组", notes = "根据keyCloakAPI创建组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "组名", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/group/{id}/children", method = RequestMethod.POST)
    @ResponseBody
    public String postGroupByParentId(@ApiIgnore KeycloakGroup group,@PathVariable String id) {
        // 调试日志
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送post请求
            String url="http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/groups/"+id+"/children";
            HttpPost post = new HttpPost(url);

            post.setHeader("Authorization", "bearer "+KeycloakTool.getToken());
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");

            String json = "{\"name\":\""+group.getName()+"\"}";
            HttpClient httpClient = new DefaultHttpClient();

            StringEntity postingString = new StringEntity(json,"UTF-8");// json传递
            post.setEntity(postingString);


            HttpResponse response = httpClient.execute(post);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                /**读取服务器返回过来的json字符串数据**/
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                return strResult;
            } else {
                logger.error("Data Uplink Failure:{}", response.getEntity().getContent());
            }
        } catch (IOException e) {
            logger.error("get request:", e);
        }
        return "创建出错";
    }



}