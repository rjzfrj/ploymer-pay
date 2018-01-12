package com.zyzf.polymer.pay.service;

import java.util.List;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponSeller;

/**
 * 商家列表service
 * @author wuhp
 */
public interface SellerListService {
	//查询优惠券商家列表
	public List<PmsCouponSeller> selectSellerList();
}
