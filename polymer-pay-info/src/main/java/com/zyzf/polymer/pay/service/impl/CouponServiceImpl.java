package com.zyzf.polymer.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zyzf.polymer.pay.dao.coupon.PmsCouponOrderMapper;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponOrder;
import com.zyzf.polymer.pay.service.CouponService;
/**
 * 优惠券订单查询实现
 * @author wuhp
 *
 */
@Service
public class CouponServiceImpl implements CouponService{
   @Autowired
   PmsCouponOrderMapper pmsCouponOrderMapper;
  
	public PmsCouponOrder selectCouponOrder(PmsCouponOrder couponOrder) {
		
		return pmsCouponOrderMapper.selectCouponOrder(couponOrder);
	}

}
