package com.zyzf.polymer.pay.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyzf.polymer.pay.common.core.utils.ServerPassUtil;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponOrder;
import com.zyzf.polymer.pay.service.CouponService;

/**
 * 优惠券订单查询
 * @author wuhp
 *
 */
@Controller
@ResponseBody
public class CouponAction {
	@Autowired
	CouponService couponService;
	@RequestMapping("/buyCoupon")
	public String buyCoupon(String action,String mcode,String tcode,String version){
		try{
		
		mcode="138458170611064";
		//创建map存放数据
		Map<String, Object> map= new HashMap<String, Object>();
		PmsCouponOrder pms=new PmsCouponOrder();
		pms.setMcode(mcode);
		//优惠券订单查询
		PmsCouponOrder order=couponService.selectCouponOrder(pms);
		map.put("action", action);
		map.put("mcode", mcode);
		map.put("tcode", tcode);
		map.put("version",version);
		
		if(order!=null){
		map.put("merchantOrderId",order);
		map.put("reqCode", "0000");
		map.put("reqMsg", "查询优惠券订单成功");
		}else{
			map.put("reqCode", "500");	
			map.put("reqMsg", "查询优惠券查询优惠券订单发生错误了");
		}
		
		//返回所需加密数据
		String result=ServerPassUtil.getPassUtil(mcode, map);
        return result;
		}catch(Exception e){
			 e.printStackTrace();
		}
		 return"";
	}

}
