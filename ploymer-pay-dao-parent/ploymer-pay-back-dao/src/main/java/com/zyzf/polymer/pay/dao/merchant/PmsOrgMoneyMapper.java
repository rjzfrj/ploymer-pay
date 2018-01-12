package com.zyzf.polymer.pay.dao.merchant;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.merchant.PmsOrgMoney;
@Mapper
public interface PmsOrgMoneyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsOrgMoney record);

    int insertSelective(PmsOrgMoney record);

    PmsOrgMoney selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsOrgMoney record);

    int updateByPrimaryKey(PmsOrgMoney record);
}