package com.ocean.lmh.base.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class FileExportUtil {
	
	private static final Log log = LogFactory.getLog(FileExportUtil.class);
	
	public static class Excel{
		
		/**
		 * 导出Excel文件
		 * 
		 * @param filename 文件名
		 * @param titles 标题名
		 * @param list 数据集合
		 */
		public static byte[] export(String filename, String titles, List<Object> list) {
			try {
				ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
				WritableWorkbook book = Workbook.createWorkbook(byteOutStream);
				WritableSheet sheet = book.createSheet("sheet1", 0);
				String[] titleArray = titles.split(",");
				Label label = null;
				for (int i = 0; i < titleArray.length; i++) {
					label = new Label(i, 0, titleArray[i]);
					sheet.addCell(label);
				}

				int j = 1;
				String cellValue = "";
				Object[] objt = null;
				for (Object obj : list) {
					objt = (Object[]) obj;
					for (int k = 0; k < objt.length; k++) {
						if (objt[k] instanceof Integer) {
							cellValue = String.valueOf((Integer) objt[k]);
						} else if (objt[k] instanceof java.sql.Date) {
							cellValue = DateUtils.format(DateUtils.FORMAT_DATE, (java.sql.Date) objt[k]);
						} else {
							cellValue = (String) objt[k];
						}
						label = new Label(k, j, cellValue);
						sheet.addCell(label);
					}
					j++;
				}
				book.write();
				book.close();
				
				return byteOutStream.toByteArray();
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Excel expert error:"+e);
				throw new RuntimeException("Excel expert error:" + e);
			}
		}
		
		
		
		/**
		 * 导入Excel文件（读取Excel文件）
		 * 
		 * @param is 输入流
		 * @param titles 标题行, 多個標題用","分開
		 * @return Map对象
		 */
		public static Map<Integer, List<String>> importExcel(InputStream is, String titles) {
			Workbook book = null;
			Sheet sheet = null;
			Map<Integer, List<String>> map = null;
			try {
				book = Workbook.getWorkbook(is);
				sheet = book.getSheet(0);
				int rows = sheet.getRows();
				int cols = sheet.getColumns();
				String[] titleArray = titles.split(",");
				map = new HashMap<Integer, List<String>>();
				if (rows > 1 && cols == titleArray.length) {
					for (int i = 1; i < rows; i++) {
						List<String> list = new ArrayList<String>();
						for (int j = 0; j < cols; j++) {
							
							Cell cell = sheet.getCell(j, i);

							if (cell.getType() == CellType.LABEL) {
								LabelCell labelCell = (LabelCell) cell;
								String cellContent = labelCell.getString();
								list.add(cellContent);

							} else if (cell.getType() == CellType.NUMBER) {
								NumberCell numberCell = (NumberCell) cell;
								String cellContent = numberCell.getContents();
								list.add(cellContent);

							} else if (cell.getType() == CellType.DATE) {
								DateCell dateCell = (DateCell) cell;
								Date dateDemo = dateCell.getDate();
								String cellContent = DateUtils.format(DateUtils.FORMAT_DATE, dateDemo);
								list.add(cellContent);

							}else{
								list.add("");
							}
						}
						map.put(i, list);
					}
				}
				book.close();
			} catch (Exception e) {
				
				throw new RuntimeException("import excel file failure! error:" + e);
			}

			return map;
		}

		/**
		 * 读取Excel文件
		 * 
		 * @param filePath 文件绝对路径
		 * @param titles 标题
		 * @return Map对象
		 */
		public static Map<Integer, List<String>> importExcel(String filePath, String titles) {
			return importExcel(IOSystem.getInputStream(filePath), titles);
		}
	}
	
	
	
	public static class XML{
		
		/**
		 * 生成XML文件
		 * 
		 * @param elements
		 *          xml文件中的主要內容节点
		 * @param dataList
		 *          数据内容
		 * @param response
		 */
		public String export(String[] elements, List<String[]> dataList) {
			
			// 创建文档对象，并且把数据添加到文档对象中
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("root");
		
			for (int i = 0; i < dataList.size(); i++) {
				Element row = root.addElement("rowDate");
				for (int j = 0; j < elements.length; j++) {
					if (dataList.get(i)[j] == null) {
						row.addElement(elements[j]).addText("空");
					} else {
						row.addElement(elements[j]).addText(dataList.get(i)[j]);
					}

				}
			}
			
			return doc.asXML();
		}
	}
	
	
	public static class TXT{
		public String export(String[] columns, List<String[]> dataList) {
			StringBuilder buffer = new StringBuilder();

			for (int i = 0; i < columns.length; i++) {

				buffer.append(columns[i]).append("\t");
			}
			buffer.append("\r\n");

			for (int i = 0; i < dataList.size(); i++) {
				String[] s = dataList.get(i);
				for (int j = 0; j < s.length; j++) {
					buffer.append(s[j]).append("\t\t");
				}
				buffer.append("\r\n");
			}
			
			return buffer.toString();
		}
	}
	
	
	public static class PDF{
		public byte[] exportPDF ( String [ ] propertise , List<String [ ]> list , HttpServletResponse response ) {
			
			com.itextpdf.text.Document doc = new com.itextpdf.text.Document ( );
			try{
				
				// 支持中文
				BaseFont bfChinese = BaseFont.createFont ( "c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H , BaseFont.EMBEDDED );
				Font FontChinese = new Font ( bfChinese , 12 , Font.NORMAL );

				PdfPTable table = new PdfPTable ( propertise.length );
				for ( int i = 0 ; i < propertise.length ; i++ ){

					PdfPCell cell = new PdfPCell ( );
					cell.addElement ( new Paragraph ( propertise [ i ] , FontChinese ) );
					cell.setVerticalAlignment ( PdfPCell.ALIGN_MIDDLE );
					cell.setHorizontalAlignment ( PdfPCell.ALIGN_CENTER );
					table.addCell ( cell );
				}
				
				for ( int i = 0 ; i < list.size ( ) ; i++ ){
					String [ ] s = list.get ( i );
					for ( int j = 0 ; j < s.length ; j++ ){
						PdfPCell cell = new PdfPCell ( );
						cell.addElement ( new Paragraph ( s [ j ] , FontChinese ) );
						cell.setVerticalAlignment ( PdfPCell.ALIGN_MIDDLE );
						cell.setHorizontalAlignment ( PdfPCell.ALIGN_CENTER );
						table.addCell ( cell );
					}

				}
				
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
				PdfWriter.getInstance ( doc , byteArrayOut);
				return byteArrayOut.toByteArray();

			}catch ( Exception ex ){
				throw new RuntimeException("Export PDF error:" + ex);
			}
		}
	}
	
}
