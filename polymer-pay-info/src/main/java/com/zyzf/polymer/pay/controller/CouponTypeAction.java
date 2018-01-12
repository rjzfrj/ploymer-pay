package com.zyzf.polymer.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.zyzf.polymer.pay.common.core.utils.ServerPassUtil;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponType;
import com.zyzf.polymer.pay.service.CouponTypeListService;
import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券商品类型列表
 * @author wuhp
 *
 */
@Controller
public class CouponTypeAction {
	@Resource
	CouponTypeListService couponTypeListService;

	@ResponseBody
	@RequestMapping("/getTypeList")
	public String getSellerList(String action,String mcode,String tcode,Integer version) {
		//创建map存放数据
	    Map<String, Object> map= new HashMap<String, Object>();
		List<PmsCouponType> typeList=couponTypeListService.selectTypeList();
		map.put("action", action);
		map.put("mcode", mcode);
		map.put("tcode", tcode);
		map.put("version", version);
		map.put("typeList", typeList);
		if(typeList.size()>=0 ){
		map.put("reqCode", "0000");
		map.put("reqMsg", "查询优惠商品类型表成功");
		}else{
		map.put("reqCode", "500");
		map.put("reqMsg", "查询优惠券商品类型列表失败了");
		}
		//返回数据
		String result=ServerPassUtil.getPassUtil(mcode, map);
        return result;
	}
	

}
