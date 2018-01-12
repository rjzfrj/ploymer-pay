package com.zyzf.polymer.pay.dao.coupon;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.coupon.PmsCoupon;

@Mapper
public interface PmsCouponMapper {
	int deleteByPrimaryKey(Long couponId);

	int insert(PmsCoupon record);

	int insertSelective(PmsCoupon record);

	PmsCoupon selectByPrimaryKey(Long couponId);

	int updateByPrimaryKeySelective(PmsCoupon record);

	int updateByPrimaryKey(PmsCoupon record);
	
	List<PmsCoupon> selectCouponList(PmsCoupon record);
	
	int batchUpdateStatus(Map<String,Object> map);
	
	/**
	 * 更具优惠券编码查询优惠券 当然查询条件也的包含状态和商品id
	 * @param record
	 * @return
	 */
	PmsCoupon selectCouponByCouponCode(PmsCoupon record);
}