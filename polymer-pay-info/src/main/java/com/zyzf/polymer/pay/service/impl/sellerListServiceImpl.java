package com.zyzf.polymer.pay.service.impl;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zyzf.polymer.pay.dao.coupon.PmsCouponSellerMapper;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponSeller;
import com.zyzf.polymer.pay.service.SellerListService;

/**
 * 商家列表实现
 * @author wuhp
 */
@Service
public class sellerListServiceImpl implements SellerListService {
	@Resource
	private PmsCouponSellerMapper couponSellerMapper;

	/**
	 * 查询商户列表
	 */
	public List<PmsCouponSeller> selectSellerList() {
		PmsCouponSeller seller=new PmsCouponSeller();
		return couponSellerMapper.selectCouponSellerList(seller);
	}

}
