package com.ocean.lmh.base.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



/**
 * 
 * 文件上传工具类
 * 文件上传到tomcat 下的 webapps\resources 文件夹下
 * @author luchuan
 * @since 1.0
 */
public class FileUploadUtil
{
    public static Logger log = LoggerFactory.getLogger(FileUploadUtil.class);
    public final static String ROOTPATH = File.separator;

    /**
     * 
     * 将上传的文件保存在服务器 fileupload/yyyy/MM/dd 文件夹下 返回文件保存后的相对路径
     * 
     * @param request
     * @param inputname
     *            对应文件上传表单中input的name 例如 'input type="file" name="file"'
     * @param FilePath 需要保存的路径
     * @return 返回文件存储的相对路径
     */

    public static String getFileRealPath(HttpServletRequest request,
        String inputName,String FilePath)
    {
        log.debug("-----------------------rootPath="+ROOTPATH);
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(inputName);
        // 获得文件名：
        String realFileName = file.getOriginalFilename();
        if (AssertHelper.isNotEmptyString(realFileName))
        {
            // 获取路径
            String tomcatPath = getTomcatPath(request);
            
            String ctxPath = tomcatPath + "resources" + ROOTPATH  + "fileupload" + ROOTPATH + FilePath + ROOTPATH;
            // 创建文件
            String randomPath = getDateDir();
            String fileSuffix = getFileSuffix(realFileName);

            File dirPath = new File(ctxPath + ROOTPATH + randomPath);
            if (!dirPath.exists())
            {
                dirPath.mkdirs();
            }
            File uploadFile = new File(ctxPath + ROOTPATH + randomPath + ROOTPATH
                    + UUID.randomUUID().toString() + fileSuffix);

            try
            {
                FileCopyUtils.copy(file.getBytes(), uploadFile);
            }
            catch (IOException e)
            {
                log.error(e.getMessage());
            }
            String result = uploadFile.getAbsolutePath();
            String pathName = result.substring(result.lastIndexOf("resources"));
            pathName = pathName.replace("\\", "/");
            return pathName;
        }
        else
        {
            log.debug("file is not found !");
        }
        return "";

    }

    public static String getDateDir()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + ROOTPATH + "MM" + ROOTPATH + "dd");
        String dir = sdf.format(new Date());

        return dir;
    }

    public static String getFileSuffix(String filename)
    {
        return filename.substring(filename.lastIndexOf("."));

    }
    
    public static String getTomcatPath(HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath(ROOTPATH);
        String contextPath = request.getContextPath( );
        String endStr = contextPath.substring(1);
        String result = realPath.substring(0,realPath.lastIndexOf(endStr));
        return result;
    }
    
   
    

}
