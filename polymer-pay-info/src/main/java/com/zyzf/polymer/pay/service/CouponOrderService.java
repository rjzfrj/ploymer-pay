package com.zyzf.polymer.pay.service;

import java.util.Date;
import java.util.Map;

import com.zyzf.polymer.pay.vo.coupon.CouponByVo;

public interface CouponOrderService {

	/**
	 * 优惠券购买
	 *
	 * @param productId
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public CouponByVo  couponBuy(String mcode,String tcode,String merchantOrderId,Date merchantOrderTime,Long productId,Integer num);
	
	/**
	 * 优惠券兑换
	 * @param mcode
	 * @param tcode
	 * @param merchantOrderId
	 * @param merchantOrderTime
	 * @param productId
	 * @param couponCode
	 * @return
	 */
	public Map<String,Object>  exchangeCoupon(String mcode,String tcode,String merchantOrderId,Date merchantOrderTime,Long productId,String couponCode);
}
