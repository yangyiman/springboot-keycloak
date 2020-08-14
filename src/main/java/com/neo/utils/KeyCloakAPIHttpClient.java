package com.neo.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 调用Restful API服务类，用于单元测试或调试已发布微服务API接口
 * @author shenzhenguo
 * 2019.7.18
 */
public class KeyCloakAPIHttpClient {
	
    private static final Logger logger = LoggerFactory.getLogger(KeyCloakAPIHttpClient.class);
	private static final String HEADER_CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String HEADER_AUTHROIZATION = "bearer ";
	private static final String CHART_FORMAT = "UTF-8";
	private static final String CONTEXT = "context";
	private static final String CONTENT_TYPE="Content-type";
	private KeyCloakAPIHttpClient()
	{
		  throw new IllegalStateException("Utility class");
	}
	/**
	 * 发送 post请求
	 * @param url
	 * @param body post 主体 json 参见AuthzApiTest
	 * @return
	 * @throws IOException 
	 */
	public static String sendHttpPost(String url, String body,String token) throws  IOException {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost=null;
		CloseableHttpResponse httpResponse=null;
		String retValue=null;
		try
		{
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			httpPost.addHeader(CONTENT_TYPE,HEADER_CONTENT_TYPE);
			httpPost.addHeader("Authorization", HEADER_AUTHROIZATION+token);
			httpPost.addHeader("Accept", HEADER_CONTENT_TYPE);
			httpPost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			retValue= EntityUtils.toString(entity, StandardCharsets.UTF_8); 
		}		
		catch(IOException e)
		{
			logger.info(CONTEXT, e);		
		}
		finally{
			releaseResource(httpClient,httpResponse);
		}
		return retValue;
	}
    
	/**
	 * 发送httpGet请求
	 * @param url
	 * @param param
	 * @return
	 * @throws URISyntaxException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked","rawtypes"}) 
	public static String sendHttpGet(String url,Map<String,String> param,String token) throws URISyntaxException, IOException ,KeyManagementException{
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;        
        String retValue=null;
        try {    

            //创建自定义的httpclient对象
			httpClient = HttpClients.createDefault();

            List postParams = new ArrayList();       
            for(Map.Entry<String, String> entry:param.entrySet()){
                postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }         
        	URIBuilder builder = new URIBuilder(url);
            builder.setParameters(postParams);            
            HttpGet get=new HttpGet(builder.build());

            get.setHeader(new BasicHeader("Authorization", HEADER_AUTHROIZATION+token));
			get.setHeader(new BasicHeader("Accept", HEADER_CONTENT_TYPE));
			get.setHeader(new BasicHeader("Content-type", HEADER_CONTENT_TYPE));

			httpResponse = httpClient.execute(get);
            HttpEntity valueEntity1 = httpResponse.getEntity();
            retValue= EntityUtils.toString(valueEntity1,StandardCharsets.UTF_8);            
        } catch (UnsupportedEncodingException e) {
        	logger.info(CONTEXT, e); 
        	releaseResource(httpClient,httpResponse);
        }
        catch (IOException e) {
        	logger.info(CONTEXT, e);       	
       	} finally{
        	releaseResource(httpClient,httpResponse);
        }
		return retValue;		
    }
	/**
	 * 发送 Put请求
	 * @param url
	 * @param jsonBody put主体 json 参见AuthzApiTest
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String sendHttpPut(String url,String jsonBody,String token) throws IOException  {
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse httpResponse=null;
		String retValue=null;
		try {
			httpClient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(url);
			httpPut.addHeader("Authorization", HEADER_AUTHROIZATION+token);
			httpPut.addHeader("Accept", HEADER_CONTENT_TYPE);
			httpPut.addHeader("Content-type", HEADER_CONTENT_TYPE);

			//组织请求参数    
			StringEntity stringEntity = new StringEntity(jsonBody, CHART_FORMAT);
			httpPut.setEntity(stringEntity);
			httpResponse = httpClient.execute(httpPut);
			HttpEntity putEntity = httpResponse.getEntity();
			String responseContent = null;
			if (putEntity != null) {
				responseContent = EntityUtils.toString(putEntity, CHART_FORMAT);
				retValue= responseContent;
			}			
			
		} 
		catch (IOException e) {
			logger.info(CONTEXT, e);  
       }finally {
    	    releaseResource(httpClient,httpResponse);
       }
		return retValue;	
		
	}
	/**
	 * 发送 Delete请求
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws Exception
	 */
	public static String sendHttpDelete(String url,String token) throws IOException  {
		CloseableHttpClient httpClient=null;
		CloseableHttpResponse httpResponse = null;
		String retValue=null;
		try {
			httpClient = HttpClients.createDefault();
			HttpDelete httpDelete = new HttpDelete(url);

			httpDelete.addHeader("Authorization", HEADER_AUTHROIZATION+token);
			httpDelete.addHeader("Accept", HEADER_CONTENT_TYPE);
			httpDelete.addHeader("Content-type", HEADER_CONTENT_TYPE);
			httpResponse = httpClient.execute(httpDelete);
			HttpEntity delEntity = httpResponse.getEntity();
			String responseContent = null;
			if (delEntity != null) {
				responseContent = EntityUtils.toString(delEntity, CHART_FORMAT);
			}			
			retValue= responseContent;
		}catch (IOException e) {
			logger.info(CONTEXT, e);  
		}
		finally {
			releaseResource(httpClient,httpResponse);
			}
		return retValue;
	}

    private static void releaseResource(CloseableHttpClient httpClient,CloseableHttpResponse httpResponse)
    {
    	 if(httpClient != null){
             try {
          	   httpClient.close();
             } catch (IOException e) {
             	logger.info(CONTEXT, e);
             }
         }
         if(httpResponse != null){
             try {
          	   httpResponse.close();
             } catch (IOException e) {
             	logger.info(CONTEXT, e);
             	
             }
         }
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString){
            	// Do nothing
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] paramArrayOfX509Certificate,
                    String paramString){
            	// Do nothing
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
}
