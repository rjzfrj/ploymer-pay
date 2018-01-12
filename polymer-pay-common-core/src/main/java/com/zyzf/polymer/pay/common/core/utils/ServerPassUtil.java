package com.zyzf.polymer.pay.common.core.utils;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.zyzf.polymer.pay.common.core.utils.encrypt.Des3;
/**
 * 服务端传输工具类
 * @author wuhp
 *
 */
public class ServerPassUtil {
	 public static String getPassUtil(String mcode,Map<String, Object> map) {
		   //base64商户号
			String baseMcode=Base64Util.getEncoder(mcode);
			//3DES报文
			String str=JSON.toJSONString(map);//转换字符串
			String des3Str=Des3.encode(str);
			System.out.println("3Desc结果为"+des3Str);
			//base64报文
			String baseStr=Base64Util.getEncoder(des3Str);
			System.out.println("Base64"+des3Str);
			//返回校验数据
			String MD5Str=MD5.mD5ofStr(des3Str);
			String result=Base64Util.getEncoder(MD5Str);
			String json=baseMcode+"|"+baseStr+"|"+result;
		 return json;
	 }

}
