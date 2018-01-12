package com.zyzf.polymer.pay.dao.merchant;

import org.apache.ibatis.annotations.Mapper;

import com.zyzf.polymer.pay.entity.merchant.PmsOrgMoneyLog;
@Mapper
public interface PmsOrgMoneyLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PmsOrgMoneyLog record);

    int insertSelective(PmsOrgMoneyLog record);

    PmsOrgMoneyLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PmsOrgMoneyLog record);

    int updateByPrimaryKey(PmsOrgMoneyLog record);
}