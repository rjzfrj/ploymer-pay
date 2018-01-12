package com.zyzf.polymer.pay.dao.coupon;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.coupon.PmsIndustryType;

@Mapper
public interface PmsIndustryTypeMapper {
	int deleteByPrimaryKey(Long typeId);

	int insert(PmsIndustryType record);

	int insertSelective(PmsIndustryType record);

	PmsIndustryType selectByPrimaryKey(Long typeId);

	int updateByPrimaryKeySelective(PmsIndustryType record);

	int updateByPrimaryKey(PmsIndustryType record);
}