package com.ocean.lmh.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketUtils {

	/**
	 * 发送HTTP POST 请求 
	 * @param ip 服务器IP
	 * @param port 服务器端口
	 * @param uri  调用的URI
	 * @param body 请求体
	 * @return  服务器返回的报文，包括应答头和应答体
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String post(String ip, int port, String uri, String body) throws UnknownHostException, IOException, InterruptedException{
		StringBuilder post = new StringBuilder();
		post.append("POST ").append(uri).append(" HTTP/1.1").append("\n");
		post.append("Host: ").append(ip).append(":").append(port).append("\n");
		post.append("Connection: Close").append("\n");
		post.append("Content-Type: ; charset=utf-8").append("\n");
		post.append("Content-Length: ").append(body.length()).append("\n");
		post.append("\n");
		post.append(body);
		
		return request(ip, port, post.toString());
	}
	
	/**
	 * 发送HTTP GET 请求 
	 * @param ip 服务器IP
	 * @param port 服务器端口
	 * @param uri 调用的URI
	 * @return 服务器返回的报文，包括应答头和应答体
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String get(String ip, int port, String uri) throws UnknownHostException, IOException, InterruptedException{
		StringBuilder get = new StringBuilder();
		get.append("GET ").append(uri).append(" HTTP/1.1").append("\n");
		get.append("Host: ").append(ip).append(":").append(port).append("\n");
		
		get.append("Connection: Close").append("\n");
		get.append("\n");
		
		return request(ip, port, get.toString());
	}
	
	
	/**
	 * 发送soap请求
	 * @param ip 服务器IP
	 * @param port 服务器端口
	 * @param methodNS 调用的方法命名空间
	 * @param methodName 调用的方法名
	 * @param paramsXml 传入的参数，XML格式
	 * @param requestUri 调用的URI
	 * @return 服务器返回的报文，包括应答头和应答体
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static String soap(String ip, int port, String methodNS, String methodName, String paramsXml, String requestUri) throws UnknownHostException, IOException, InterruptedException{
		
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\"?>").append("\n");
		xml.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" soap:encodingStyle=\"http://www.w3.org/2001/12/soap-encoding\">").append("\n");
		xml.append("<soap:Body xmlns:m=\"").append(methodNS).append("\">").append("\n");
		xml.append("<m:").append(methodName).append(">\n");
		xml.append(paramsXml).append("\n");
		xml.append("</m:").append(methodName).append(">\n");
		xml.append("</soap:Body>");
		xml.append("</soap:Envelope>");
	    
		StringBuilder soap = new StringBuilder();
		soap.append("POST ").append(requestUri).append(" HTTP/1.1").append("\n");
		soap.append("Content-Type: text/soap+xml; charset=utf-8").append("\n");
		soap.append("Content-Length: ").append(xml.length()).append("\n");
		soap.append("SOAPAction: ").append("\n");
		soap.append("Host: ").append(ip).append(":").append(port).append("\n");
		soap.append("Connection: Close").append("\n");
		soap.append("\n");
		soap.append(xml);
		
		System.out.println(soap);
		System.out.println();
		
		return request(ip, port, soap.toString());
	}
	
	
	/**
	 * 模拟HTTP请求
	 * @param ip 目标IP地址
	 * @param port	目标端口
	 * @param message 要请求的报文
	 * @return 返回HTTP响应的信息，包括请求头和请求体
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "resource", "static-access" })
	public static String request(String ip, int port, String message) throws UnknownHostException, IOException, InterruptedException {
			Socket socket = new Socket(ip, port);
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			pw.println(message);
			
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder httpResponse = new StringBuilder();
			while(true){
				if(reader.ready()){
					String line = null;
					while( (line = reader.readLine()) != null){
						System.out.println(line); 
						httpResponse.append(line).append("\n");
					}
					break;
				}
				Thread.currentThread().sleep(50);
			}
			
			return httpResponse.toString();
	}
}
