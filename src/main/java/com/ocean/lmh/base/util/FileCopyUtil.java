package com.ocean.lmh.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 文件操作类
 * 主要用来把文件复制到某个目录下、删除目录和删除文件
 * @author chengbujun
 * @since 1.0
 */
public class FileCopyUtil
{
	private static Logger log = LoggerFactory.getLogger(FileCopyUtil.class);
	
    public final static String ROOTPATH = File.separator;

    /**
     * 复制文件，将文件source拷贝到target路径下，
     * @param source 源文件
     * @param target 目标文件
     */
	public static void copyFileIntoNewDirectory(String source, String target)
    {
        File sourceFile = new File(source);
        File targetFile = new File(target);

        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try
        {
        	//判断目标文件是否存在
        	if (!targetFile.exists())
        	{
        		if (!targetFile.getParentFile().exists())
        		{
        			targetFile.getParentFile().mkdirs();
        		}
        		targetFile.createNewFile();
        	}
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024];
            int len;
            while ((len = inBuff.read(b)) != -1)
            {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        }catch(Exception e){
        	log.error("拷贝文件出错："+e.getMessage());
        }
        finally
        {
            try {
            	// 关闭流
                if (inBuff != null){
                    inBuff.close();
                    inBuff = null;
                }
                if (outBuff != null){
                    outBuff.close();
                    outBuff = null;
                }
			} catch (Exception e2) {
			}
        }
    }
    
    /**
     * 文件夹拷贝，将源文件夹中的所有文件拷贝到目标文件夹中去
     * @param source 源文件夹
     * @param target 目标文件夹
     */
    public static void copyFileFromDirectory(String source, String target)
    {
    	File file = new File(source);
    	
    	//如果源文件不是文件夹，不做任何处理
    	if (!file.isDirectory()) {
			return;
		}
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try
        {
            // 获取源文件夹当前下的文件或目录
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File sourceFile = files[i];
                if (sourceFile.isFile()) {
                    // 源文件
                    File targetFile = new File(target + ROOTPATH + sourceFile.getName());
                    if (!targetFile.exists())
                    {
                        targetFile.createNewFile();
                    }
                    copyFileIntoNewDirectory(sourceFile.getAbsolutePath(), targetFile.getAbsolutePath());
                }else{
                    File t = new File(target + ROOTPATH + sourceFile.getName());
                    if(!t.exists())
                    {
                        t.mkdirs();
                    }
                    copyFileFromDirectory(source + ROOTPATH + sourceFile.getName(), target + ROOTPATH + sourceFile.getName());
                }
                
            }
            
        }catch(Exception e){
        	log.error("拷贝文件出错："+e.getMessage());
        }
        finally
        {
            try {
            	// 关闭流
                if (inBuff != null){
                    inBuff.close();
                    inBuff = null;
                }
                if (outBuff != null){
                    outBuff.close();
                    outBuff = null;
                }
			} catch (Exception e2) {
			}
        }
    }
    
    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(targetDir + ROOTPATH + file[i].getName());
                
                if (!targetFile.exists())
                {
                    if (!targetFile.getParentFile().exists())
                    {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                }
                BufferedInputStream inBuff = null;
                BufferedOutputStream outBuff = null;
                try
                {
                    // 新建文件输入流并对它进行缓冲
                    inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

                    // 新建文件输出流并对它进行缓冲
                    outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

                    // 缓冲数组
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = inBuff.read(b)) != -1)
                    {
                        outBuff.write(b, 0, len);
                    }
                    // 刷新此缓冲的输出流
                    outBuff.flush();
                }
                finally
                {
                    // 关闭流
                    if (inBuff != null)
                        inBuff.close();
                    if (outBuff != null)
                        outBuff.close();
                }
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
    
    
    /**
     * 删除目录
     * 
     * @param sPath 被删除文件夹的文件名，需要是绝对路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath)
    {
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory())
        {
            return false;
        }
        
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            // 删除子文件
            if (files[i].isFile())
            {
                deleteFile(files[i].getAbsolutePath());
            } // 删除子目录
            else
            {
                deleteDirectory(files[i].getAbsolutePath());
            }
        }
       
        // 删除当前目录
        if (dirFile.delete())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath)
    {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
