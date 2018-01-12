package com.zyzf.polymer.pay.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.zyzf.polymer.pay.common.core.utils.Base64Util;
import com.zyzf.polymer.pay.common.core.utils.encrypt.Des3;
import com.zyzf.polymer.pay.common.core.utils.encrypt.RSAEncrypt;
@RequestMapping("/forward")
@Controller
public class repeaterController {
	
	

	@RequestMapping("/fcn.action")
	public String forward(String str,  HttpServletRequest request, HttpServletResponse response) {
		String action = null;
		try {
			if (StringUtils.isBlank(str)) {

				PrintWriter out = response.getWriter();
				Map map = new HashMap();
				map.put("reqCode", "9999");
				map.put("reqMsg", "传送参数为空");
				String ret = JSON.toJSONString(map);
				out.print(ret);
				out.close();
			}
			String a[] = str.split("\\|");
			//
			String mcode = Base64Util.getResolveDecoder(a[0]);
			String deskeym = Base64Util.getResolveDecoder(a[1]);
			String data = Base64Util.getResolveDecoder(a[2]);
			String privateKey = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBALMKHB14AYUbGVYDQjwYJC2dXatQ79yoZyqbexXmInc17Y38nA2XG72PAd6mzS15OUG4jPpZ2qKNy3Ju2avIGeO7obLc7l9Ry2U5GU1JmNSAoxj4Hu7gqH6uH3PDppMvGuGsvTvdphuh8PUyBV2ArfeuIgQwEHMmKRuc1JMf9yefAgMBAAECgYEAq4V28+BhDGSuvBE5Jhs2paCJ+TL76BhJZa84lv03bZ9zasBjvD1UrSBQ+T4xeGwXJ1gnbzwf0fUnwARkckasTTrO678EvppT6/QdHQiHo73mXTbhHaYeuZz4dshxpxoQxoE5p119zOwpA+wHP/sATz1f05i8F1ulK2L2fuT1v1kCQQD2eqazqD2yRXyHsLZnMvNBF0WFRzs97DuvHTRTBDbxg/J6J1H2dias1kk3ZpFfM/lXAOW6bqdPr7ZNSWHmu+WLAkEAufSSLogkW9kk3PeboLqEgFb/pY5XyXxM7hARMLkhYmWXYepd2gl6CCyqQQpeFJ1+mLKodxfO6clnCfvPk3IQvQJBAK1Qc1XTEIcBa15yeOA6KGh1t7HO865IJXOuInPZe9T5xg/1BCI81FcmWtd07PW3szVlcSRV2Jok4RiZaBj5uJ8CQQC3B60is8nlxbNs8yJxb2Sf1gHG+HYb0Yb/Az2IUZA0g34fWEmMJKvChAQIBKZZcDN9JMyAfCSjJCORRhMoM6uZAkEAtsl6PUdrHKyZgS/SMBgPsJ48K3y2L2QtzYOjQg/YlGLLaOuNrjM7NdeU4NlgXflorz9ts8Cpd7dUH75sHRZHRg==";
			String deskey = RSAEncrypt.decryptForPrKey(privateKey, deskeym);
			String dataJson = Des3.decode(data, deskey);
			Map<String, String> mapdata = JSON.parseObject(dataJson, Map.class);
			for (Map.Entry<String, String> entity: mapdata.entrySet()) {
				request.setAttribute(entity.getKey(), entity.getValue());
			}
			action = mapdata.get("action");
			//
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "forward:/" + action;
	}

}
