package com.zyzf.polymer.pay.service;

import java.util.List;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponSeller;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponType;

/**
 * 商品类型列表service
 * @author wuhp
 */
public interface CouponTypeListService {
	//查询优惠券商品类型列表
	public List<PmsCouponType> selectTypeList();
}
