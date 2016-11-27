package com.ocean.lmh.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpClientUtils {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtils.class);
	
	private static final DefaultHttpClient HTTP_CLIENT = new DefaultHttpClient();
	
	private static final String	LOCAL_HOST	= "http://127.0.0.1:9300/mobile_anhui/";
	private static final String	SERVER_HOST	= "http://61.191.45.194:8282/mobile_anhui/";
	private static String charsetName = "utf-8";
	
	@Test
	public void test() throws Exception{
		
		String url = "http://127.0.0.1:9400/game_management/game/login.do";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", "tjp");
		HttpClientResponse result = HttpClientUtils.post(url, map);
		System.out.println(LOCAL_HOST + result + SERVER_HOST);
	}
	
	/**
	 * jsonPost
	 * @param url
	 * @param jsonParams
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResponse jsonPost(String url,String jsonParams) throws Exception{
		return jsonPost(url, jsonParams, null);
	}
	
	public static HttpClientResponse jsonPost(String url,String jsonParams, String jsessionId) throws Exception{
		
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
		if( AssertHelper.isNotEmptyString(jsessionId) ){
			httpPost.setHeader("Cookie", "JSESSIONID=" + jsessionId);
		}
		httpPost.setEntity(new ByteArrayEntity(jsonParams.getBytes("UTF-8")));  
		
		return exe(httpPost);
	}
	
	public static HttpClientResponse jsonPost(String url, Map<String,Object> params ) throws Exception{
		return jsonPost(url, params, null);
	}
	public static HttpClientResponse jsonPost(String url, Map<String,Object> params, String jsessionId) throws Exception{
		String jsonParams = JSONHelper.toJson(params);
		return jsonPost(url, jsonParams, jsessionId);
	}
	
	
	public static HttpClientResponse post(String url, Map<String,Object> params) throws Exception{
		return post(url, toPostParams(params), null);
	}
	public static HttpClientResponse post(String url, Map<String,Object> params, String jsessionId) throws Exception{
		return post(url, toPostParams(params), jsessionId);
	}
	public static HttpClientResponse post(String url, String params) throws Exception{
		return post(url, params, null);
	}
	public static HttpClientResponse post(String url, String params, String jsessionId) throws Exception{
		
		HttpPost httpPost = new HttpPost(url);

		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		if( AssertHelper.isNotEmptyString(jsessionId) ){
			httpPost.setHeader("Cookie", "JSESSIONID=" + jsessionId);
		}
		httpPost.setEntity(new ByteArrayEntity(params.getBytes("UTF-8")));  
		
		return exe(httpPost);
	}
	
	/**
	 * GET
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResponse get(String url, Map<String, Object> params ) throws Exception{
		return get(url, params, null);
	}
	
	public static HttpClientResponse get(String url, Map<String, Object> params, String jsessionId) throws Exception{
		
		return get(url, toGetParams(params), jsessionId);
	}
	
	public static HttpClientResponse get(String url, String params) throws Exception{
		return get(url, params, null);
	}
	
	public static HttpClientResponse get(String url, String params, String jsessionId) throws Exception{
		
		HttpGet httpGet = new HttpGet(url+params);
		httpGet.setHeader("Cookie", "JSESSIONID="+jsessionId);
		return exe(httpGet);
	}
	
	private static HttpClientResponse exe(HttpRequestBase request) throws IOException{
		
		HttpResponse response = HTTP_CLIENT.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();
		
		Header[] headers = response.getAllHeaders();
		for(Header header : headers){
			System.out.println(header.getName()+":"+header.getValue());
		}
		
		HttpEntity entity = response.getEntity();
		String entityStr = null;
		if(entity.isStreaming()){
			
			InputStream in = entity.getContent();
			try{
				entityStr = IOSystem.readToString(in);
			}catch(Exception e){
			}
		}
		
		EntityUtils.consume(entity);
		System.out.println("HTTP status code:"+statusCode);
		System.out.println("HTTP response body:"+entityStr);
		
		return new HttpClientResponse(statusCode, entityStr);
	}
	
	
	private static String toGetParams(Map<String, Object> params){
		
		return toParams(params, true);
		
	} 
	
	private static String toPostParams(Map<String, Object> params){
		
		return toParams(params, false);
		
	} 
	
	private static String toParams(Map<String, Object> params, boolean isGet){
		
		if(params == null || params.isEmpty()){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		if(isGet){
			
		}
		Set<Entry<String, Object>> entries = params.entrySet();
		for(Entry<String, Object> entry : entries){
			sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString()));
		}
		
		if(isGet){
			sb.replace(0, 1, "?");
		}else{
			sb.replace(0, 1, "");
		}
		
		return sb.toString();
		
	} 
	
	public static String post2(final String url, final String content){
		if (log.isDebugEnabled()) {
			log.debug("\nrequest url:\n{}\nrequest content:\n{}", url, content);
		}
		
		HttpClientBuilder clientBuilder = HttpClients.custom();
		clientBuilder.setRetryHandler(new HttpRequestRetryHandler() {
			@Override
			public boolean retryRequest(IOException ioex, int retryCount, HttpContext httpContext) {
				log.error("http request retry [" + retryCount+" "+url+"]");
				try {
					if(retryCount>3) {
						return false;
					}else if (retryCount==1) {
						System.err.println(content+"date1="+new Date());
						Thread.sleep(5000);
						return true;
					}else if (retryCount==2) {
						System.err.println(content+"date=2"+new Date());
						Thread.sleep(10000);
						return true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		CloseableHttpClient httpclient = clientBuilder.build();
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json;charset=" + charsetName);
			post.setEntity(new StringEntity(content, charsetName));
			HttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			String rsp = EntityUtils.toString(entity, charsetName);
			if (log.isDebugEnabled()) {
				log.debug("\nresponse:\n{}", rsp);
			}
			return rsp;
		} catch (Exception e) {
			log.error("调用失败", e);
			throw new RuntimeException(e);
		} finally {
			try {
				httpclient.close();
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
		}
	}
}

