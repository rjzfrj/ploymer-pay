package com.zyzf.polymer.pay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyzf.polymer.pay.dao.coupon.PmsCouponProductMapper;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponProduct;
import com.zyzf.polymer.pay.service.CouponProducListService;
/**
 * 优惠券商品列表
 * @author wuhp
 */
@Transactional //事务管理
@Service
public class CouponProducListServiceImpl implements CouponProducListService {
	@Autowired
	PmsCouponProductMapper pmsCouponProductMapper;
	/**
	 * 查询优惠券产品
	 */
	public List<PmsCouponProduct> selectCouponProductList() {
		return pmsCouponProductMapper.selectProductList();
	}

}
