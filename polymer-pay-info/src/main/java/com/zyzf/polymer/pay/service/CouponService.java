package com.zyzf.polymer.pay.service;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponOrder;

/**
 * 优惠券订单查询Service
 * @author wuhp
 *
 */
public interface CouponService {
   public PmsCouponOrder selectCouponOrder(PmsCouponOrder couponOrder);
}
