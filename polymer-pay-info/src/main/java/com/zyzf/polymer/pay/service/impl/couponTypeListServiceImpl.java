package com.zyzf.polymer.pay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zyzf.polymer.pay.dao.coupon.PmsCouponTypeMapper;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponType;
import com.zyzf.polymer.pay.service.CouponTypeListService;
/**
 * 优惠券商品类型列表
 * @author wuhp
 */
@Service
public class couponTypeListServiceImpl  implements CouponTypeListService{
    @Resource
	private PmsCouponTypeMapper pmsCouponTypeMapper;
	public List<PmsCouponType> selectTypeList() {
		
		return pmsCouponTypeMapper.selectCouponTypeList();
	}

}
