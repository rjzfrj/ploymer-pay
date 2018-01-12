package com.zyzf.polymer.pay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyzf.polymer.pay.common.core.utils.ServerPassUtil;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponProduct;
import com.zyzf.polymer.pay.service.CouponProducListService;

/**
 * 优惠券产品列表
 * @author wuhp
 *
 */
@Controller
public class CouponProductAction {
	@Resource
	private CouponProducListService couponProducListService;

	@ResponseBody
	@RequestMapping("/getProductList")
	public String getSellerList(String action,String mcode,String tcode,Integer version,
			Integer sellerId,Integer typeId) {
		//创建map存放数据
	    Map<String, Object> map= new HashMap<String, Object>();
		List<PmsCouponProduct> productList=couponProducListService.selectCouponProductList();
		map.put("action", action);
		map.put("mcode", mcode);
		map.put("tcode", tcode);
		map.put("version", version);
		map.put("productList", productList);
		if(productList.size()>=0 ){
		map.put("reqCode", "0000");
		map.put("reqMsg", "查询优惠券商户列表成功");
		}else{
		map.put("reqCode", "500");
		map.put("reqMsg", "查询优惠券商户列表失败了");
		}
		String result=ServerPassUtil.getPassUtil(mcode, map);
        return result;
	}
	
	

}
