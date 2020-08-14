package com.neo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.keycloak.admin.client.Keycloak;

public class KeycloakTool {
	 public static String getToken()
	    {				
	        try { 

	        	 HttpPost post = new HttpPost("http://127.0.0.1:8180/auth/realms/quickstart-vanilla/protocol/openid-connect/token");
	             post.setHeader("Accept", "application/json");
	             post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	            
	             
	             String charSet = "UTF-8";
	            
	             CloseableHttpResponse response = null;
	               List<NameValuePair> urlParameters = new ArrayList<>();
	                urlParameters.add(new BasicNameValuePair("client_id","app-authz-vanilla"));
	                urlParameters.add(new BasicNameValuePair("grant_type","password"));
	                urlParameters.add(new BasicNameValuePair("username","admin"));
	                urlParameters.add(new BasicNameValuePair("password","admin"));

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


	public static String getTokenByadminclient(){
		Keycloak keycloak = Keycloak.getInstance("http://localhost:8180/auth",// keycloak地址
				//"master",// 指定 Realm master
				"quickstart-vanilla",
				"admin",// 管理员账号
				"admin",// 管理员密码
				// 指定client（admin-cli是Master Realm中的内置client,Direct Access
				// Grants Enabled）
				//"admin-cli"
				"app-authz-vanilla");

		// 取得accesstoken
		String accessToken = keycloak.tokenManager().getAccessTokenString();
		return accessToken;
	}
}
