package com.ocean.lmh.base.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * 文件解压工具类，可解压winrar/winzip压缩的文件，也可进行预览
 * 
 * @author liminghui
 *
 */
public class DeCompressUtil {
	
	private static Logger log = LoggerFactory.getLogger(DeCompressUtil.class);

	/**
	 * 解压压缩类型为rar的文件,使用的是unrar第三方工具包
	 * 
	 * @param rarFile
	 *            zip压缩文件源路径
	 * @param destDir
	 *            解压后的目标路径
	 * @return 成功返回true，失败返回false
	 */
	public static boolean extractRarFile(String rarFile, String destDir) throws Exception {
		Archive ar = new Archive(new File(rarFile));
		destDir = destDir.endsWith("/") ? destDir : destDir + File.separator;
		FileOutputStream fos = null;
		String fileName = null;
		String dir = null;
		File parent = null;
		try {
			for (FileHeader fh : ar.getFileHeaders()) {
				if (!fh.isDirectory())
					continue;
				fileName = fh.getFileNameString().trim();
				dir = destDir + fileName.replaceAll("\\\\", "/");
				parent = new File(dir);
				if (!parent.exists()) {
					parent.mkdirs();
				}
			}

			for (FileHeader fh : ar.getFileHeaders()) {
				if (fh.isDirectory())
					continue;
				//判断编码，解决中文文件名时的乱码问题
				if (fh.isUnicode()) {
					fileName = fh.getFileNameW().trim();
				}else{
					fileName = fh.getFileNameString().trim();
				}
				fileName = destDir + fileName.replaceAll("\\\\", "/");
				fos = new FileOutputStream(fileName);
				ar.extractFile(fh, fos);
				fos.close();
			}

		} catch(Exception e){
			log.error("解压rar文件出错："+e.getMessage());
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
				ar.close();
				ar = null;
			} catch (Exception e) {
				log.error("解压rar文件后关闭流对象出错："+e.getMessage());
			}
		}
		return true;
	}

	/**
	 * 解压压缩类型为zip的文件，使用的是org.apache.tools.zip
	 * 和Java自带的解压工具类操作方式基本一致，不过后者支持压缩文件中存在中文。
	 * @param zipFile
	 *            zip压缩文件源路径
	 * @param destDir
	 *            解压后的目标路径
	 * @param encodingType
	 *            解压文件时指定的编码格式
	 * @return 成功返回true，失败返回false
	 */
	public static boolean extractZipFile(String zipFile, String destDir, String encodingType) {
		destDir = destDir.endsWith("/") ? destDir : destDir + File.separator;
		InputStream in = null;
		FileOutputStream fos = null;
		String fileName = null;
		String dir = null;
		File parent = null;
		ZipFile zip = null;
		try {
			//先创建文件夹路径
			zip = new ZipFile(zipFile,encodingType);
			Enumeration<?> entries = zip.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (!entry.isDirectory())
					continue;
				fileName = entry.getName();
				dir = destDir + fileName;
				parent = new File(dir);
				parent.mkdirs();
			}
			
			//然后输出文件内容
			entries = zip.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()){
					continue;
				}
				fileName = entry.getName();
				fileName = destDir + entry.getName();
				
				//判断父类文件夹是否存在
				File outFile = new File(fileName);
				if (!outFile.getParentFile().exists()) {
					outFile.getParentFile().mkdirs();
				}
				
				fos = new FileOutputStream(outFile);
				in = zip.getInputStream(entry);
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = in.read(buf)) != -1) {
					fos.write(buf, 0, len);
				}
				fos.close();
				in.close();
			}
		} catch(Exception e){
			e.printStackTrace();
			log.error("解压zip文件出错："+e.getMessage());
			return false;
		} finally {
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
				zip.close();
				zip = null;
			} catch (Exception e) {
				log.error("解压zip文件后关闭流对象出错："+e.getMessage());
			}
		}
		return true;
	}

	/** 打印用WinRar压缩的文档 */
	public static void printRarEntryList(File rarFile, Writer out, String lineSplitChar) {
		Archive archive = null;
		try {
			archive = new Archive(rarFile);
			for (FileHeader fh : archive.getFileHeaders()) {
				String fileName = fh.getFileNameString();
				out.write(fileName + lineSplitChar);
				out.flush();
			}
		} catch (Exception e){
			log.error("printRarEntryList:"+e.getMessage());
		} finally {
			try {
				out.close();
				archive.close();
			} catch (Exception e2) {
			}
		}

	}

	/** 打印用WinZip压缩的文档 */
	public static void printZipEntryList(File zipFile, Writer out, String lineSplitChar) {
		ZipFile archive = null;
		try {
			archive = new ZipFile(zipFile);
			Enumeration<ZipEntry> entries = archive.getEntries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				String fileName = entry.getName();
				out.write(fileName + lineSplitChar);
				out.flush();
			}
		} catch (Exception e){
			log.error("printRarEntryList:"+e.getMessage());
		} finally {
			try {
				out.close();
				archive.close();
			} catch (Exception e2) {
			}
		}

	}
}
