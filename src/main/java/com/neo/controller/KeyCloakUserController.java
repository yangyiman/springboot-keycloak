package com.neo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo.config.BaseResult;
import com.neo.utils.KeycloakTool;
import com.neo.model.User;
import io.swagger.annotations.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Api(value = "KeyCloak用户管理", description = "KeyCloakAPI", position = 200, protocols = "http")
@RestController
@RequestMapping(value = "/keycloak")
public class KeyCloakUserController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());
    private final static Logger logger = LoggerFactory.getLogger(KeyCloakUserController.class);
    @ApiOperation(value = "获取用户列表", notes = "查询用户列表")
    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getUserList() {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            //发送get请求
            String url = "http://127.0.0.1:8180/auth/admin/realms/quickstart-vanilla/users";

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

    @ApiOperation(value = "获取操作管理员token", notes = "管理员")
    @RequestMapping(value = {"/token"}, method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "passsword", value = "密码", required = true, dataType = "String", paramType = "query")
    })
    @ApiResponses({@ApiResponse(code = 100, message = "异常数据")})
    @ResponseBody
    public String getToken(@ApiIgnore User user) {

            try {

                HttpPost post = new HttpPost("http://127.0.0.1:8180/auth/realms/quickstart-vanilla/protocol/openid-connect/token");
                post.setHeader("Accept", "application/json");
                post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
                String charSet = "UTF-8";

                CloseableHttpResponse response = null;
                List<NameValuePair> urlParameters = new ArrayList<>();
                urlParameters.add(new BasicNameValuePair("client_id","app-authz-vanilla"));
                urlParameters.add(new BasicNameValuePair("grant_type","password"));
                urlParameters.add(new BasicNameValuePair("username",user.getName()));
                urlParameters.add(new BasicNameValuePair("password",user.getPasssword()));

                post.setEntity(new UrlEncodedFormEntity(urlParameters, charSet));

                CloseableHttpClient httpclient = HttpClients.createDefault();

                try {
                    post.setEntity(new UrlEncodedFormEntity(urlParameters, HTTP.UTF_8));
                    try {
                        response = httpclient.execute(post);
                        // 判断网络连接状态码是否正常(0--200都数正常)
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                            JSONObject jsonObject = JSON.parseObject(content);

                            String token = jsonObject.getString("access_token");

                            System.out.println(token);

                            return token;
                        }
                        EntityUtils.consume(response.getEntity());//完全消耗
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != response) response.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } finally {
                    //释放链接
                    try {
                        httpclient.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return null;


    }

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String postUser(@ApiIgnore User user) {
        try {

            Keycloak keycloak = Keycloak.getInstance("http://localhost:8180/auth",// keycloak地址
                    //"master",// 指定 Realm master
                    "quickstart-vanilla",
                    "admin",// 管理员账号
                    "admin",// 管理员密码
                    // 指定client（admin-cli是Master Realm中的内置client,Direct Access
                    // Grants Enabled）
                    "admin-cli");
            // 进入 testRealmName01
            RealmResource realmResource = keycloak.realm("quickstart-vanilla");

            // 新建用户
            UserRepresentation userRepresentation = new UserRepresentation();
            // 设置登录账号
            userRepresentation.setUsername(user.getName());
            // 设置账号“启用”
            userRepresentation.setEnabled(true);
            // 设置密码
            List<CredentialRepresentation> credentials = new ArrayList<CredentialRepresentation>();
            CredentialRepresentation cr = new CredentialRepresentation();
            cr.setType(CredentialRepresentation.PASSWORD);
            cr.setValue("123456");
            cr.setTemporary(false);
            credentials.add(cr);
            userRepresentation.setCredentials(credentials);

            //设置自定义用户属性
            Map<String, List<String>> attributes = new HashMap<String, List<String>>();
            List<String> list = new ArrayList<String>();
            list.add("音乐");
            list.add("美术");
            attributes.put("爱好", list);
            userRepresentation.setAttributes(attributes);

            // 创建用户
            realmResource.users().create(userRepresentation);

            return  "成功";
        } catch (Exception e) {
            logger.error("get request:", e);
        }

        return "创建出错";
    }



   /* @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)*/
    public User getUser(@PathVariable Long id) {
        return users.get(id);
    }

   /* @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "年龄", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)*/
    public BaseResult<User> putUser(@PathVariable Long id, @ApiIgnore User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return BaseResult.successWithData(u);
    }

   /* @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)*/
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

   /* @RequestMapping(value = "/ignoreMe/{id}", method = RequestMethod.DELETE)*/
    public String ignoreMe(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }
}