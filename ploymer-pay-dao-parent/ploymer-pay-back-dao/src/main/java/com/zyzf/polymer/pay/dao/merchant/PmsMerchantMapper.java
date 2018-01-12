package com.zyzf.polymer.pay.dao.merchant;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.merchant.PmsMerchant;
@Mapper
public interface PmsMerchantMapper {
    int deleteByPrimaryKey(String mcode);

    int insert(PmsMerchant record);

    int insertSelective(PmsMerchant record);

    PmsMerchant selectByPrimaryKey(String mcode);

    int updateByPrimaryKeySelective(PmsMerchant record);

    int updateByPrimaryKey(PmsMerchant record);
}