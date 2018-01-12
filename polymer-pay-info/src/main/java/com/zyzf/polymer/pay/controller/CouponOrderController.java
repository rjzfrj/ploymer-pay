package com.zyzf.polymer.pay.controller;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zyzf.polymer.pay.common.core.utils.BeanUtil;
import com.zyzf.polymer.pay.common.core.utils.DateUtils;
import com.zyzf.polymer.pay.common.core.utils.ServerPassUtil;
import com.zyzf.polymer.pay.service.CouponOrderService;
import com.zyzf.polymer.pay.vo.coupon.CouponByVo;

@Controller
public class CouponOrderController {
	@Resource
	private CouponOrderService couponOrderService;

	
	@ResponseBody
	@RequestMapping("/couponAction_buyCoupon")
	public String buyCoupon(HttpServletRequest request, HttpServletResponse response,String action,String mcode,String tcode,String version,Long productId,Integer num,String merchantOrderId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date merchantOrderTime) {
		//使用productId查询到商品详情
		CouponByVo vo=null;
		try {
			action=(String) request.getAttribute("action");
			mcode=(String) request.getAttribute("mcode");
			tcode=(String) request.getAttribute("tcode");
			version=(String) request.getAttribute("version");
			productId=Long.valueOf( request.getAttribute("productId").toString());
			num=Integer.valueOf(request.getAttribute("num").toString());
			merchantOrderId=(String) request.getAttribute("merchantOrderId");
			merchantOrderTime=DateUtils.getDateByStr(request.getAttribute("merchantOrderTime").toString()) ;
			vo=couponOrderService.couponBuy(mcode, tcode, merchantOrderId, merchantOrderTime, productId, num);
		} catch (Exception e) {
			vo=new CouponByVo();
			vo.setReqCode("9999");
			System.out.println(e.getMessage());
			vo.setReqMsg(e.getMessage());
			
		}
		Map<String, Object> map=BeanUtil.beanToMap(vo);
		String ret=ServerPassUtil.getPassUtil(mcode, map);
		return ret;
	}
/*	@ResponseBody
	@RequestMapping("/couponAction_buyCoupon")
	public String buyCoupon(String action,String mcode,String tcode,String version,Long productId,Integer num,String merchantOrderId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date merchantOrderTime) {
		//使用productId查询到商品详情
		CouponByVo vo=null;
		try {
			vo=couponOrderService.couponBuy(mcode, tcode, merchantOrderId, merchantOrderTime, productId, num);
		} catch (Exception e) {
			vo=new CouponByVo();
			vo.setReqCode("9999");
			System.out.println(e.getMessage());
			vo.setReqMsg(e.getMessage());
			
		}
		Map<String, Object> map=BeanUtil.beanToMap(vo);
		String ret=ServerPassUtil.getPassUtil(mcode, map);
		return ret;
	}
*/	
	
	@ResponseBody
	@RequestMapping("/couponAction_exchangeCoupon")
	public String exchangeCoupon(String action,String mcode,String tcode,String version,Long productId,String couponCode,String merchantOrderId,@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")Date merchantOrderTime) {
		//使用productId查询到商品详情
		Map<String,Object> vo=null;
		try {
			vo=couponOrderService.exchangeCoupon(mcode, tcode, merchantOrderId, merchantOrderTime, productId, couponCode);
			vo.put("action", action);
			vo.put("version", version);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String ret=ServerPassUtil.getPassUtil(mcode, vo);
		return ret;
	}
}
