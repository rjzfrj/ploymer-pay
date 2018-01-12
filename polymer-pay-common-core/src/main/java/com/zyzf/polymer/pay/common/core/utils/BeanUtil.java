package com.zyzf.polymer.pay.common.core.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * <p>
 * Project:					polymer-pay-common-core
 * <p>
 * Module ID:				<模块类编号可以引用系统设计中的类编号>
 * <p>
 * Comments:				<对此类的描述>
 * <p>
 * JDK version used:		JDK1.7
 * <p>
 * NameSpace:				com.zyzf.polymer.pay.common.core.utils.BeanUtil.java
 * <p>
 * Author:					liuzf
 * <p>
 * Create Date:				2017年5月26日
 * <p>
 * Modified By:				<修改人中文名或拼音缩写>
 * <p>
 * Modified Date:			<修改日期>
 * <p>
 * Why & What is modified:	<修改原因描述>
 * <p>
 * Version:					v1.0
*/ 
public class BeanUtil extends org.apache.commons.beanutils.BeanUtils{

    /**
     *将javabean实体类转为map类型，然后返回一个map类型的值
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> beanToMap(Object obj) { 
            Map<String, Object> params = new HashMap<String, Object>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name)) { 
                    	if( propertyUtilsBean.getNestedProperty(obj, name)!=null){
                    		  params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    	}
                      
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
	
	
	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param dest
	 *            目标对象，标准的JavaBean
	 * @param orig
	 *            源对象，可为Map、标准的JavaBean
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static void applyIf(Object dest, Object orig) throws Exception {
		try {
			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					if (PropertyUtils.isWriteable(dest, name)) {
						Object value = ((Map) orig).get(name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			} else {
				java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();
					if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}

	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param orig
	 *            源对象，标准的JavaBean
	 * @param dest
	 *            排除检查的属性，Map
	 * 
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkObjProperty(Object orig, Map dest) throws Exception {
		try {
			java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (!dest.containsKey(name)) {
					if (PropertyUtils.isReadable(orig, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value == null) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}
}
