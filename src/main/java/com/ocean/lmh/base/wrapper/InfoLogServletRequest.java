package com.ocean.lmh.base.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.base.util.IOSystem;
import com.ocean.lmh.base.util.StringUtils;

public class InfoLogServletRequest extends HttpServletRequestWrapper {

	private byte[] jsonContent;
	private boolean isJsonContent;
	private HttpServletRequest request;
	
	public InfoLogServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
		
		isJsonContent = HttpRequestUtils.isJsonContent(request);
		if(isJsonContent){
			try {
				BufferedReader br = request.getReader();
				String sJsonContent = IOSystem.read(br);
				jsonContent = StringUtils.getBytes(sJsonContent);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	@Override
	public BufferedReader getReader() throws IOException {
		if(isJsonContent){
			return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(jsonContent)));
		}else{
			return request.getReader();
		}
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if(isJsonContent){
			return new MyServletInputStream(jsonContent);
		}else{
			return request.getInputStream();
		}
	}
	
	
	private class MyServletInputStream extends ServletInputStream{
		private byte[] jsonContent;
		private int position;
		
		public MyServletInputStream(byte[] jsonContent){
			this.jsonContent = jsonContent;
			position = 0;
		}
		
		@Override
		public int read() throws IOException {
			
			if(position >= jsonContent.length){
				return -1;
			} 
			return jsonContent[position++];
		}
		
	}

}
