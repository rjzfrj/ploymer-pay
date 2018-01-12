package com.zyzf.polymer.pay.common.core.utils.city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CityUtil {
	
	//List<Map<省节点值, List<Map<市节点值,市节点中文名>>>>
	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	
	//Map<省节点值, 省节点中文名>
	private static List<Map<String, String>> provinceHash = new ArrayList<Map<String, String>>();
	
	//Map<市节点值, 市节点中文名>
	private static Map<String,String> allCityHash = new HashMap<String,String>();
	
	//Map<省节点值, LinkedHashMap<市节点值,市节点中文名>
	private static Map<String, List<Map<String, String>>> cityHash = new HashMap<String, List<Map<String, String>>>();
	
	//Map<省/市节点值, 省/市节点中文名>
	private static Map<String, String> allMap = new TreeMap<String, String>();//包含省和市
	
	//Map<市节点中文名, 市节点值>
	private static Map<String,String> allNameMap = new HashMap<String,String>();
	
	//Map<市节点中文名, 市节点值>
	private static Map<String,String> allNameCityMap = new HashMap<String,String>();
	
	//城市 下一个城市的code  查询支行需要 <code,nextCode>
	private static Map<String, String> nextCityCodeMap = new TreeMap<String, String>();//包含省和市
	
	static {
		parse();
	}
	
	public static List<Map<String, Object>> getCityList() {
		return list;
	}
	
	public static List<Map<String, String>> getProvinceMap(){
		return provinceHash;
	}
	
	public static Map<String, List<Map<String, String>>> getCityMap(){
		return cityHash;
	}
	
	public static Map<String,String> getAllCityMap(){
		
		return allCityHash;
	}
	
	/**
	 * 由省/市的值, 得到对应中文名
	 * @param value 省/市的值
	 * @return
	 */
	public static String getNameByValue(String value) {
		String outValue = allMap.get(value);
		if(null == outValue || "".equals(outValue)){
			outValue = "北京市";
		}
		return outValue;
	}
	
	/**
	 * 由省/市的值, 得到对应中文名
	 * @param value 省/市的值
	 * @return
	 */
	public static String getValueByName(String name,int type) {
		String outValue = "";
		if(1 == type){//
			outValue = allNameMap.get(name);
		}else{
			outValue = allNameCityMap.get(name);
		}
		if(null == outValue || "".equals(outValue)){
			outValue = "1000";
		}
		return outValue;
	}
	
	/**
	 * 由省的值, 得到市的集合
	 * @param value
	 * @return
	 */
	public static List<Map<String, String>> getCitiesByValue(String value){
		return cityHash.get(value);
	} 
	
	@SuppressWarnings("unchecked")
	private static void parse() {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(CityUtil.class.getClassLoader().getResourceAsStream("com/zyzf/polymer/pay/common/core/utils/city/city.xml"));
			LinkedHashMap<String, Object> elementMap = null;
			Element root = document.getRootElement();
			
			Map<String,String> provinceMap = null;
			//所以的城市代码
			List<String> allCityCode = new ArrayList<String>();
			// 遍历根结点root的所有子节点element
			for (Iterator itElement = root.elementIterator(); itElement.hasNext();) {
				elementMap = new LinkedHashMap<String, Object>();
				Element element = (Element) itElement.next();//省节点
				String name = element.element("name").getText();//省节点对应的中文名
				elementMap.put("name", name);
				String value = element.element("value").getText();//省节点对应的值
				elementMap.put("value", value);
				//添加
				provinceMap = new HashMap<String, String>();
				provinceMap.put(value, name);
				provinceHash.add(provinceMap);
				
				allMap.put(value, name);
				allNameMap.put(name, value);
				Element children = element.element("children");
				List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
				LinkedHashMap<String, String> childMap = null;
				List<Map<String, String>> cityList = new ArrayList<Map<String, String>>();
				//遍历省节点下的所有市节点
				for (Iterator itChild = children.elementIterator(); itChild.hasNext();) {
					LinkedHashMap<String, String> childMap2 = new LinkedHashMap<String, String>();
					childMap = new LinkedHashMap<String, String>();
					Element child = (Element) itChild.next();//市节点
					String childName = child.element("name").getText();//市节点对应的中文名
					childMap.put("name", childName);
					String childValue = child.element("value").getText();//市节点对应的值
					
					childMap2.put(childValue, childName);
					childList.add(childMap2);
					
					allCityHash.put(childValue, childName);
					childMap2.put(childValue, childName);
					cityList.add(childMap2);
					allMap.put(childValue, childName);
					allNameCityMap.put(childName, childValue);
					//添加城市代码
					allCityCode.add(childValue);
				}
				//所在市 添加全部选项
				LinkedHashMap<String, String> childMap3 = new LinkedHashMap<String, String>();
				childMap3.put("", "全部");
				childList.add(0,childMap3);

				//cityHash.put(value, childMap2);
				cityHash.put(value, childList);
				elementMap.put("children", childList);
				
				list.add(elementMap);
			}
			
			//排序
			Collections.sort(allCityCode, new Comparator<String>(){
				public int compare(String c1, String c2) {
					int code1 = Integer.parseInt(c1);
					int code2 = Integer.parseInt(c2);
					if(code1 < code2){
						return -1 ;
					}else if(code1 > code2){
						return 1 ;
					}else{
						return 0;
					}
				}
			});
			
			//初始化下一个城市Map
			for(int i = 0; i < allCityCode.size(); i++){
				if(i == (allCityCode.size()-1)){
					nextCityCodeMap.put(allCityCode.get(i), "10000");
				}else{
					nextCityCodeMap.put(allCityCode.get(i), allCityCode.get(i+1));
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回下一个 Code  用于支行查询
	 * @param Code
	 * @return
	 */
	public static Integer getNextCityCode(String Code){
		return Integer.parseInt(nextCityCodeMap.get(Code))-1;
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//log.debug(cityHash.entrySet());
		/*log.debug(provinceHash.get("1"));
		for (Map<String, Object> map : list) {
			String name = (String) map.get("name");
			String value = (String) map.get("value");
			List<Map<String, String>> childList = (List<Map<String, String>>) map.get("children");
			log.debug(name+"  "+value);
			for (Map<String, String> child : childList) {
				name = (String) child.get("name");
				value = (String) child.get("value");
				log.debug(name + " - " + value);
			}
		}*/
//		log.debug(cityHash);
//		log.debug("");
//		log.debug(provinceHash);
		
		Iterator pIt = provinceHash.iterator();
		while (pIt.hasNext()) {
			Map<String,String> p = (Map<String, String>) pIt.next();
			Set pKeySet = p.keySet();
			Iterator it=pKeySet.iterator();
			while( it .hasNext() ){
				//log.debug(it.next()+" === " +p.get(it.next().t));
				String key = (String) it.next();
				provinceMap.put(p.get(key)+"", Integer.parseInt(key));
			}
		}
		
		//获得所有城市
		Set pKeySet = allCityHash.keySet();
		pIt = pKeySet.iterator();
		while (pIt.hasNext()) {
			String key = (String) pIt.next();
			cityMap.put(allCityHash.get(key), Integer.parseInt(key));
		}
//		log.debug(provinceMap);
//		log.debug("");
//		log.debug(cityMap);
	}

	static Map<String,Integer> provinceMap = new HashMap<String,Integer>();
	static Map<String,Integer> cityMap = new HashMap<String,Integer>();
}
