package com.zyzf.polymer.pay.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponSeller;

@Mapper
public interface PmsCouponSellerMapper {
	int deleteByPrimaryKey(Long sellerId);

	int insert(PmsCouponSeller record);

	int insertSelective(PmsCouponSeller record);

	PmsCouponSeller selectByPrimaryKey(Long sellerId);

	int updateByPrimaryKeySelective(PmsCouponSeller record);

	int updateByPrimaryKey(PmsCouponSeller record);
	
	public List<PmsCouponSeller>  selectCouponSellerList(PmsCouponSeller record);
}