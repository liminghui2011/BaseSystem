package com.ocean.lmh.base.util;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils {
	
	/**
	 * 此方法获取冲xml中获取指定key的值，返回Map对象，其中Map中的key值即是传入的key值
	 * @param in
	 * @param rootName 所指xml根元素,表示解析哪个节点;若包含此节点，key中可以省略此节点名
	 * @param keys
	 * @return
	 */
	public static Map<String, String> parse(InputStream in,String rootName,String[] keys){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(in);
		} catch (DocumentException e) {
			throw new RuntimeException("XmlUtils parse() error:"+e);
		}

		Element root = document.getRootElement();
		Element rootElement = find(root, rootName);

		Map<String,String> map = new HashMap<String, String>();
		for(String key : keys){
			Element tempEle = find(rootElement, key);
			String value = null;
			if(tempEle != null){
				value = tempEle.getText();
			}
			map.put(key, value);
		}
		
		return map;
		
	}
	
	private static Element find(Element rootElement, String key){
		
		if(key.indexOf(".") == -1){
			return rootElement.element(key);
		}else{
			String[] nodeNames = key.split("\\.");
			Element tempEle = rootElement;
			for(String node : nodeNames){
				if(tempEle != null){
					tempEle = tempEle.element(node);
				}else{
					break;
				}
			}
			
			return tempEle;
		}
	}
	
	/**
	 * 此方法获取冲xml中获取指定key的值，返回Map对象，其中Map中的key值即是传入的key值
	 * @param xml
	 * @param rootName 所指xml根元素,表示解析哪个节点
	 * @param keys
	 * @return
	 */
	public static Map<String, String> parse(byte[] xml,String rootName,String[] keys){
		return parse(new ByteArrayInputStream(xml),rootName,keys) ;
	}
	
	
	/**
	 * 此方法获取冲xml中获取指定key的值，返回Map对象，其中Map中的key值即是传入的key值
	 * @param xml String
	 * @param keys
	 * @return
	 */
	public static Map<String, String> subXml(String xml,String[] keys){
		
		Map<String,String> map = new HashMap<String, String>();
		for(String key : keys ){
			String value = subXml(xml, key);
			map.put(key, value);
		}
		
		return map;
	}
	
	
	private static String subXml(String xml,String key){
		String[] keys = key.split("\\.");
		
		String returnXml = xml;
		for(String smallKey : keys){
			returnXml = subXmlSingel(returnXml, smallKey);
			if(AssertHelper.isEmptyString(returnXml)){
				break;
			}
		}
		
		return returnXml;
	}
	
	private static String subXmlSingel(String xml,String key){
		String startStr = "<"+key+">";
		String endStr = "</"+key+">";
		
		int startIndex = xml.indexOf(startStr);
		int endIndex = xml.indexOf(endStr);
		
		if(startIndex != -1 && endIndex != -1 && startIndex < endIndex){
			return xml.substring(startIndex + startStr.length(), endIndex);
		}
		
		return "";
	}
	
	public static void main(String[] args) throws DocumentException {
		
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TVPay><Mac></Mac><SecureData>${SecureData}</SecureData><TVPaybody><TermStatus>1</TermStatus><Msg><version>01.1.0.0</version><typeID>0200</typeID></Msg><ProcessingCode>191000</ProcessingCode><EntryCode>011</EntryCode><PosCondCode>81</PosCondCode><xclass>KP</xclass><Bill><Type>01</Type><Number>${Bill.Number}</Number><areaCode>4201</areaCode><Month>${Bill.Month}</Month></Bill><acctNum>${acctNum}</acctNum><safetyVerifyMode>1</safetyVerifyMode><Merchant><acqBIN>${Merchant.acqBIN}</acqBIN><fwdBIN>${Merchant.fwdBIN}</fwdBIN><termUnitNo>${Merchant.termUnitNo}</termUnitNo><userID>${Merchant.userID}</userID><termTypeID>${Merchant.termTypeID}</termTypeID><merID>${Merchant.merID}</merID><name>${Merchant.name}</name></Merchant><Purchase><termAcAbility>0</termAcAbility><icCondCode>0</icCondCode><termID>${Purchase.termID}</termID><traceNum>${Purchase.traceNum}</traceNum><date>${Purchase.date}</date><purchAmount>${Purchase.purchAmount}</purchAmount><currency>156</currency></Purchase><channelType>16</channelType><PubKeyIndex>${PubKeyIndex}</PubKeyIndex><SecRelContInfo>${SecRelContInfo}</SecRelContInfo></TVPaybody></TVPay>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TVPay><Mac></Mac><SecureData>20202020202020203A8FB5E32C4BC6C1AF7388DDF18945FC718CA89920202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020070603020600202020202020202020202020</SecureData><TVPaybody><TermStatus>1</TermStatus><Msg><version>10000</version><typeID>0100</typeID></Msg><ProcessingCode>311000</ProcessingCode><EntryCode>011</EntryCode><PosCondCode>92</PosCondCode><xclass>KF</xclass><Bill><Type>03</Type><Number>44556677</Number><areaCode>4201</areaCode><Month>201212</Month></Bill><safetyVerifyMode>1</safetyVerifyMode><Merchant><acqBIN>[acqBIN]</acqBIN><fwdBIN>[fwdBIN]</fwdBIN><termUnitNo>tjp</termUnitNo><userID>tjp</userID><termTypeID>[termTypeID.终端种类标识]</termTypeID><merID>[water.merID.商户代码]</merID><name>[water.name.商户名称和所在地]</name></Merchant><Purchase><termAcAbility>0</termAcAbility><icCondCode>0</icCondCode><termID>[termID]</termID><traceNum>014410</traceNum><date>0106094945</date><currency>156</currency></Purchase><PubKeyIndex>3</PubKeyIndex><details><userName>张*三</userName><userAddress>武汉市武昌区****06号</userAddress><userWaterCost>152.7</userWaterCost></details><STPP><traceNum>123456</traceNum><date>0106094945</date></STPP><Resp><respCode>10</respCode><respInfo>ONLY FOR TEST  汉字测试，乱码</respInfo></Resp></TVPaybody></TVPay>";
		String[] keys = {
				"Msg.typeID","Purchase.traceNum","Purchase.date","Purchase.date.wa","details"
		};
		
		Map<String,String> map = parse(new ByteArrayInputStream(xml.getBytes()),"TVPaybody",keys);
		
		System.out.println(map);
		
		
		map = subXml(xml,keys);
		System.out.println(map);
	}
}
