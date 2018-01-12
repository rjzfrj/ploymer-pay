package com.zyzf.polymer.pay.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.coupon.PmsCouponType;

@Mapper
public interface PmsCouponTypeMapper {
	int deleteByPrimaryKey(Long typeId);

	int insert(PmsCouponType record);

	int insertSelective(PmsCouponType record);

	PmsCouponType selectByPrimaryKey(Long typeId);

	int updateByPrimaryKeySelective(PmsCouponType record);

	int updateByPrimaryKey(PmsCouponType record);
	
	/**
	 * 查询商品类型类型列表
	 */
	public List<PmsCouponType> selectCouponTypeList();
}