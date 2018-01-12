package com.zyzf.polymer.pay.common.core.utils;

import java.lang.reflect.Field;
import java.util.TreeMap;

/**
 * @author liuzf
 * 
 */
public class ReflectionUtils {

	/**
	 * @param bean
	 * @return Map
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static TreeMap<String, Object> convertBeanToMap(Object bean)
			throws IllegalArgumentException, IllegalAccessException {
		if (bean != null) {

			TreeMap<String, Object> map = new TreeMap<String, Object>();
			Class<?> clazz = bean.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					if (!"serialVersionUID".equals(field.getName())) {
						if (field.get(bean) != null) {
							map.put(field.getName(), field.get(bean));
						}
					}
				}
			}
			return map;
		}else{
			return null;
		}
	}
}
