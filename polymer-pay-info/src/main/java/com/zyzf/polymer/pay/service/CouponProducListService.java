package com.zyzf.polymer.pay.service;

import java.util.List;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponProduct;

/**
 * 查询优惠券商品列表service
 * @author wuhp
 */
public interface CouponProducListService {
	//查询优惠券商品列表
	public List<PmsCouponProduct> selectCouponProductList();
}
