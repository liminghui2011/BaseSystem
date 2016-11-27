package com.ocean.lmh.base.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.output.FileWriterWithEncoding;

/**
 * 
 * 数据输入、输出相关方法集合
 *
 */
public class IOSystem {
	
	/**
	 * 将内容写入到给定的path。注意若path已经存在，会覆盖原文件
	 * @param content 输入内容
	 * @param path 输出地址
	 */
	public static void writeTo(String content,String path){
		
		writeTo(content, path, false);
	}
	
	/**
	 * 将内容写入到给定的path
	 * @param content 输入内容
	 * @param path 输出地址
	 * @param append 是否追加。true 追加，false 不追加
	 */
	public static void writeTo(String content,String path,boolean append){
		
		try {
			
			makeDir(path);
			
			FileWriter fileWriter = new FileWriter(path,append);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
			
		} catch (Exception e) {
			throw new RuntimeException("Write to file failure! exception is : " + e);
		}
	}
	
	 /**
     * 写入文件，如果不存在自动新建，如果已存在则覆盖
     * @param content 文件内容
     * @param path 写入文件的路径
     */
    public static void writeToUTF8(String content,String path)
    {
        try
        {
            makeDir(path);
            //带编码的文件写入类，如果文件不存在则会自动新建，存在则会覆盖
            FileWriterWithEncoding fw=new FileWriterWithEncoding(path,Charset.forName("utf-8"));
            fw.append(content);
            fw.close();
            
        }
        catch (Exception e)
        {
            throw new RuntimeException("Write to file failure! exception is : " + e);
        }
    }
	
	private static void makeDir(String path){
		
		path = path.replaceAll("\\\\", "/");
		
		int endIndex = path.lastIndexOf("/");
		String dirPath = path.substring(0, endIndex);
		File file = new File(dirPath);
		
		if(!file.exists()){
			file.mkdirs();
		}
		
	}
	/**
	 * 将内容写入到给定的path
	 * @param content 输入字节数组
	 * @param path 输出地址
	 */
	public static void writeTo(byte[] content,String path){
		try{
			FileOutputStream fileOut = new FileOutputStream(path);
			
			fileOut.write(content);
			fileOut.flush();
			fileOut.close();
			
		}catch(Exception e){
			throw new RuntimeException("Write to file failure! exception is : " + e);
		}
	}
	
	
	/**
	 * 通过path读取内容
	 * @param path 文件地址
	 * @return byte[]
	 */
	public static byte[] read(String path){
		try{
			return readToBytes(getInputStream(path));
		}catch(Exception e){
			throw new RuntimeException("Read file failure! exception is : " + e);
		}
	}
	
	/**
	 * 通过path获取InputStream流
	 * @param path	文件地址
	 * @return InputStream流
	 */
	public static InputStream getInputStream(String path){
		
		try {
			return new FileInputStream(path);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException("getInputStream failure! exception is : " + e);
		}
	}
	
	/**
	 * 读取InputStream流中的数据
	 * @param in 输入流
	 * @return byte[] 输出字节数组
	 */
	public static byte[] readToBytes(InputStream in){
		try {
			byte[] bytes = new byte[in.available()];
			in.read(bytes);
			
			return bytes;
		} catch (Exception e) {
			throw new RuntimeException("IOSystem readToBytes failure: "+e);
		}
		
	}
	
	/**
	 * 读取InputStream流中的数据
	 * @param in 输入流
	 * @return String 输出字符串，UTF-8格式
	 */
	public static String readToString(InputStream in){
		return StringUtils.toString(readToBytes(in));
	}
	
	/**
	 * 读取path地址中的数据
	 * @param path 输入地址
	 * @return String 输出字符串，UTF-8格式
	 */
	public static String readToString(String path){
		return StringUtils.toString(read(path));
	}
	
	 /**
     * 根据传入的路径以UTF-8的方式读取文件
     * @param path 传入的路径
     * @return 文件的内容
     * @exception/throws 文件读取失败
     *
     */
    public static String readToStringUTF8(String path)
    {
        try
        {
            FileInputStream fis=new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String s="";
            StringBuffer sb=new StringBuffer();
            while((s=br.readLine())!=null)
            {
                sb.append(s);
            }
            br.close();
            return sb.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException("IOSystem readToString failure: "+e);
        }
    }
	
	/**
	 * 判断文件或者目录是否存在
	 * @param path 输入地址
	 * @return boolean true存在，false 不存在
	 */
	public static boolean isFileExist(String path) {
		File file = new File(path);
		return file.exists();
	}
	
	/**
	 * 判断文件或者目录是否存在
	 * @param path 输入地址
	 * @return boolean false存在，true不存在
	 */
	public static boolean isFileNotExist(String path) {
		//File file = new File(path);
		return !isFileExist(path);
	}
	
	/**
	 * 解压缩zip格式并读取数据
	 * @param in 输入流
	 * @return String 字符串数据，UTF-8格式
	 * @throws IOException
	 */
	public static String unZipToString(InputStream in) throws IOException {
		return StringUtils.toString(unZip(in));
	}
	
	/**
	 * 解压缩zip格式并读取数据
	 * @param in 输入流
	 * @return byte[] 字节数组
	 * @throws IOException
	 */
	public static byte[] unZip(InputStream in) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(in);
		return readToBytes(gzipInputStream);
	}
	
	/**
	 * 删除文件或者目录
	 * @param path 路径
	 */
	public static void delete(String path) {
		File file = new File(path);
		file.delete();
	}
	
	
	/**
	 * 从Reader中读取数据，并转换为字符串
	 * @param reader 
	 * @return String
	 */
	private static final char[] CHARS = new char[1024];
	public static String read(Reader reader){
		StringBuilder sb = new StringBuilder();
		
		int count;
		try {
			while( (count = reader.read(CHARS)) != -1){
				sb.append(new String(CHARS, 0, count));
			}
		} catch (IOException e) {
			throw new RuntimeException("IOSystem read failure: "+e);
		}
		
		return sb.toString();
	}
}
