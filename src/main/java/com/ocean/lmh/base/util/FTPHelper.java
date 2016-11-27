package com.ocean.lmh.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * @author 凌剑东
 */

/**
 * ftp工具类，实现ftp上传、下载
 * @author pengjunguang
 *
 */
public class FTPHelper {
	private static FTPHelper instance = new FTPHelper();
	private FTPClient ftpClient =null;
	
	/**
	 * 单例模式，获取工具类对象
	 * @return FTPHelper
	 */
	public static FTPHelper getInstance(){
		return instance;
	}

	
	/**
	 * ftp登录
	 * @param s_url ftp服务地址
	 * @param uname 用户名
	 * @param pass  密码
	 */
	public void login(String s_url,int port,String uname,String pass){
		ftpClient = new FTPClient();
		try{
			//连接
			ftpClient.connect(s_url,port);
			ftpClient.login(uname,pass);
			//检测连接是否成功
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
				this.closeCon();
				System.err.println("FTP server refused connection.");
				System.exit(1);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.closeCon();
		}
	}
	
	
	/**
	 * ftp上传文件
	 * @param fromDir 上传目录
	 * @param srcUrl 须上传文件
	 * @param targetFname 生成目标文件
	 * @return true||false
	 */
	public boolean uploadFile(String fromDir,String srcUrl,String targetFname){
		boolean flag = false;
		if( ftpClient!=null ){
			 File srcFile = new File(srcUrl); 
			 FileInputStream fis = null;
			 try {
				fis  = new FileInputStream(srcFile);
				
				//设置上传目录 
				ftpClient.changeWorkingDirectory(fromDir);
	            ftpClient.setBufferSize(1024); 
	            ftpClient.setControlEncoding("UTF-8"); 
	            
	            //设置文件类型（二进制） 
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	            //上传
	            flag = ftpClient.storeFile(new String(targetFname.getBytes("UTF-8"),"ISO-8859-1"), fis); 
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				IOUtils.closeQuietly(fis);
				this.closeCon();  
			}
		}
		return flag;
	}
	
	
	/**
	 * 删除ftp上的文件
	 * @param srcFname ftp文件物理路径
	 * @return true || false
	 */
	public boolean removeFile(String srcFname){
		boolean flag = false;
		if( ftpClient!=null ){
			try {
				ftpClient.enterLocalPassiveMode();
				flag = ftpClient.deleteFile(new String(srcFname.getBytes("UTF-8"),"ISO-8859-1"));
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				this.closeCon();  
			}
		}
		return flag;
	}
	
	/**
	 *销毁ftp连接
	 */
	public void closeCon(){
		if(ftpClient !=null){
			if(ftpClient.isConnected()){
				try {
					ftpClient.logout();
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	 
	/** 
     * 从FTP服务器下载文件 
     * @param remotePath FTP服务器上的相对路径 
     * @param fileName 要下载的文件名 
     * @param localPath 下载后保存到本地的路径 
     * @return 
     */  
    public boolean downFile(String remotePath, String fileName, String localPath) {  
        boolean success = false;  
        try {  
            ftpClient.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
            FTPFile[] fs = ftpClient.listFiles();  
            for (FTPFile ff : fs) {  
            	String fName=new String(ff.getName().getBytes("iso-8859-1"),"UTF-8");
                if (fName.equals(fileName)) {  
                    File localFile = new File(localPath);  
                    OutputStream is = new FileOutputStream(localFile);  
                    ftpClient.retrieveFile(ff.getName(), is);  
                    is.close();  
                    success = true;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
        	this.closeCon();  
        }  
        return success;  
    }  
	
    
    /**
     * 解析ftp文件路径，活动ftp地址、用户名、密码、文件名、文件相对路径
     * @param ftpFilePath   ftp路径   例如：ftp://song:song@172.16.4.220/bq/t.mpg
     * @return 包含上述内容的Map对象
     */
	 public static Map<String,String> getFtpMap(String ftpFilePath){
		 Map<String,String> map =new HashMap<String,String>();
		 try{
			 String path=ftpFilePath.substring(ftpFilePath.indexOf("//")+2);
			 String[] msg=path.split("@");
			 String[] user_pwd=msg[0].split(":");
			 String ip_dir=msg[1];
			 map.put("user", user_pwd[0]);
			 map.put("password", user_pwd[1]);
			 map.put("ip", ip_dir.substring(0,ip_dir.indexOf("/")));
			 map.put("fileDir", ip_dir.substring(ip_dir.indexOf("/"),ip_dir.lastIndexOf("/")+1));
			 map.put("fileName", ip_dir.substring(ip_dir.lastIndexOf("/")+1));
			 map.put("fullName", ip_dir.substring(ip_dir.indexOf("/")));
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return map;
	 }
    
	 public static void main(String[] args) throws IOException, InterruptedException
	 {  
		 FTPHelper  instance=FTPHelper.getInstance();
		 instance.login("10.104.0.11",21, "song", "lutong");
		 //System.out.println(instance.removeFile("/test - 副本.xls"));
		 //instance.downFile("/cache/hw/bq/","歌曲模板2.xls","G:\\ftp\\test\\test000.xls");
	 //instance.downFile("/bq/","爱亮晶晶.mpg","G:\\ftp\\test");
		 System.out.println(instance.removeFile("/cache/hw/bq/t_role啦啦啦.sql"));
	  //System.out.println(getFtpMap("ftp://song:song@172.16.4.220/bq/t.mpg"));
	 }
	 
	 
}
