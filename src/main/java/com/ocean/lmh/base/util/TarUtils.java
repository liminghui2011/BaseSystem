package com.ocean.lmh.base.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/** 
 * 压缩文件
 * 功能：压缩文件成tar.gz格式或者tar格式 
 * @author chengbujun
 * @since 1.0
 */
public class TarUtils
{
    private static int BUFFER = 1024 * 4; // 缓冲大小
    private static byte[] B_ARRAY = new byte[BUFFER];
    public final static String ROOTPATH = File.separator;
    
    public static void main ( String [ ] args ) throws Exception {
        
    }
    
    /**
	 * 打包成zip文件
	 * 
	 * @param out
	 * @param source
	 * @param base
	 * @throws Exception
	 */
	public static void packAsZip(ZipOutputStream out, String source, String base) throws Exception {
		source = source.replaceAll("\\\\", "/");
		File file = new File(source);
		String entryName = source.substring(base.length() + 1);
		if (file.isDirectory()) {
			out.putNextEntry(new ZipEntry(entryName + "/"));
			for (File child : file.listFiles()) {
				packAsZip(out, child.getAbsolutePath(), base);
			}
		} else {
			out.putNextEntry(new ZipEntry(entryName));
			IOUtils.write(IOUtils.toByteArray(new FileInputStream(file)), out);
		}
	}
    
    /**
     * 方法功能：把单个文件或者文件夹只打包成tar
     * 参数：inputFileName:要打包的文件夹或文件的路径    targetFileName:打包后的文件路径
     */
    public void tar(String inputFileName, String targetFileName, HttpServletRequest request)
    {
        String tomcatPath = getTomcatPath(request);
        
        File inputFile = new File(tomcatPath + ROOTPATH + inputFileName);
        String base = inputFileName.substring(inputFileName.lastIndexOf("/") + 1);
        TarOutputStream out = getTarOutputStream(tomcatPath + ROOTPATH + targetFileName);
        tarPack(out, inputFile, base);
        try
        {
            if (null != out)
            {
                out.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 方法功能：把单个文件或者文件夹只打包成tar.gz
     *  参数：srcFile 要压缩的tar文件路径
     */
    public void targz(String inputFileName, String targetFileName, HttpServletRequest request)
    {
        String tomcatPath = getTomcatPath(request);
        
        //打成tar的扎包
        File inputFile = new File(tomcatPath + ROOTPATH + inputFileName);
        String base = inputFileName.substring(inputFileName.lastIndexOf("/") + 1);
        TarOutputStream tout = getTarOutputStream(tomcatPath + ROOTPATH + targetFileName);
        tarPack(tout, inputFile, base);
        try
        {
            if (null != tout)
            {
                tout.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //打成tar.gz的扎包
        File srcFile = new File(tomcatPath + ROOTPATH + targetFileName + ".tar");
        File target = new File(tomcatPath + ROOTPATH + srcFile.getAbsolutePath() + ".gz");
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try
        {
            in = new FileInputStream(srcFile);
            out = new GZIPOutputStream(new FileOutputStream(target));
            int number = 0;
            while ((number = in.read(B_ARRAY, 0, BUFFER)) != -1)
            {
                out.write(B_ARRAY, 0, number);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }

                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        srcFile.delete();
    }
    
    /**
     * 方法功能：把单个文件或者文件夹同时打包成tar和tar.gz
     *  参数：srcFile 要压缩的tar文件路径
     */
    public void tarAndGz(String inputFileName, String targetFileName, HttpServletRequest request)
    {
        String tomcatPath = getTomcatPath(request);
        
        //打成tar的扎包
        File inputFile = new File(tomcatPath + ROOTPATH + inputFileName);
        String base = inputFileName.substring(inputFileName.lastIndexOf("/") + 1);
        TarOutputStream tout = getTarOutputStream(tomcatPath + ROOTPATH + targetFileName);
        tarPack(tout, inputFile, base);
        try
        {
            if (null != tout)
            {
                tout.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //打成tar.gz的扎包
        File srcFile = new File(tomcatPath + ROOTPATH + targetFileName + ".tar");
        File target = new File(srcFile.getAbsolutePath() + ".gz");
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try
        {
            in = new FileInputStream(srcFile);
            out = new GZIPOutputStream(new FileOutputStream(target));
            int number = 0;
            while ((number = in.read(B_ARRAY, 0, BUFFER)) != -1)
            {
                out.write(B_ARRAY, 0, number);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }

                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 方法功能：打包多个文件或文件夹 
     * 参数：inputFileNameList 要打包的文件夹或文件的路径的列表 targetFileName
     * 打包后的文件路径
     */
    public void execute(List<String> inputFileNameList, String targetFileName, HttpServletRequest request)
    {
        String tomcatPath = getTomcatPath(request);
        
        TarOutputStream out = getTarOutputStream(tomcatPath + ROOTPATH + targetFileName);

        for (String inputFileName : inputFileNameList)
        {
            File inputFile = new File(tomcatPath + ROOTPATH + inputFileName);
            String base = inputFileName.substring(inputFileName.lastIndexOf("/") + 1);
            tarPack(out, inputFile, base);
        }

        try
        {
            if (null != out)
            {
                out.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 方法功能：打包成tar文件 
     * 参数：out 打包后生成文件的流 inputFile 要压缩的文件夹或文件 base 打包文件中的路径
     */

    public void tarPack(TarOutputStream out, File inputFile, String base)
    {
        if (inputFile.isDirectory()) // 打包文件夹
        {
            packFolder(out, inputFile, base);
        }
        else
        // 打包文件
        {
            packFile(out, inputFile, base);
        }
    }

    /**
     * 方法功能：遍历文件夹下的内容，如果有子文件夹，就调用tarPack方法 参数：out 打包后生成文件的流 inputFile 要压缩的文件夹或文件
     * base 打包文件中的路径
     */
    public void packFolder(TarOutputStream out, File inputFile, String base)
    {
        File[] fileList = inputFile.listFiles();
        try
        {
            // 在打包文件中添加路径
            out.putNextEntry(new TarEntry(base + "/"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        base = base.length() == 0 ? "" : base + "/";
        for (File file : fileList)
        {
            tarPack(out, file, base + file.getName());
        }
    }

    /**
     * 方法功能：打包文件 
     * 参数：out 压缩后生成文件的流 inputFile 要压缩的文件夹或文件 base 打包文件中的路径
     */
    public void packFile(TarOutputStream out, File inputFile, String base)
    {
        TarEntry tarEntry = new TarEntry(base);

        // 设置打包文件的大小，如果不设置，打包有内容的文件时，会报错
        tarEntry.setSize(inputFile.length());
        try
        {
            out.putNextEntry(tarEntry);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(inputFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        int b = 0;

        try
        {
            while ((b = in.read(B_ARRAY, 0, BUFFER)) != -1)
            {
                out.write(B_ARRAY, 0, b);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != in)
                {
                    in.close();
                }
                if (null != out)
                {
                    out.closeEntry();
                }
            }
            catch (IOException e)
            {

            }
        }
    }

    /**
     * 方法功能：获得打包后文件的流 
     * 参数：targetFileName 打包后文件的路径
     */
    public TarOutputStream getTarOutputStream(String targetFileName)
    {
        // 如果打包文件没有.tar后缀名，就自动加上
        targetFileName = targetFileName.endsWith(".tar") ? targetFileName : targetFileName + ".tar";
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(targetFileName);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        TarOutputStream out = new TarOutputStream(bufferedOutputStream);

        // 如果不加下面这段，当压缩包中的路径字节数超过100 byte时，就会报错
        out.setLongFileMode(TarOutputStream.LONGFILE_GNU);
        return out;
    }
    
    /**
     * 获取tomcat的根路径
     */
    public static String getTomcatPath(HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath(ROOTPATH);
        String contextPath = request.getContextPath( );
        String endStr = contextPath.substring(1);
        String result = realPath.substring(0,realPath.lastIndexOf(endStr));
        return result;
    }

}
