package com.zyzf.polymer.pay.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zyzf.polymer.pay.common.core.utils.DateUtils;
import com.zyzf.polymer.pay.dao.coupon.PmsCouponMapper;
import com.zyzf.polymer.pay.dao.coupon.PmsCouponOrderMapper;
import com.zyzf.polymer.pay.dao.coupon.PmsCouponProductMapper;
import com.zyzf.polymer.pay.dao.merchant.PmsMerchantMapper;
import com.zyzf.polymer.pay.dao.merchant.PmsOrgMoneyLogMapper;
import com.zyzf.polymer.pay.dao.merchant.PmsOrgMoneyMapper;
import com.zyzf.polymer.pay.dao.merchant.PmsTerminalMapper;
import com.zyzf.polymer.pay.entity.coupon.PmsCoupon;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponOrder;
import com.zyzf.polymer.pay.entity.coupon.PmsCouponProduct;
import com.zyzf.polymer.pay.entity.merchant.PmsMerchant;
import com.zyzf.polymer.pay.entity.merchant.PmsOrgMoney;
import com.zyzf.polymer.pay.entity.merchant.PmsOrgMoneyLog;
import com.zyzf.polymer.pay.entity.merchant.PmsTerminal;
import com.zyzf.polymer.pay.service.CouponOrderService;
import com.zyzf.polymer.pay.vo.coupon.CouponByVo;
import com.zyzf.polymer.pay.vo.coupon.CouponVo;
@Service
public class CouponOrderServiceImpl implements CouponOrderService {
	
	@Resource
	private PmsCouponOrderMapper couponOrderMapper;
	@Resource
	private PmsCouponProductMapper couprProductMapper;
	@Resource
	private PmsMerchantMapper merchantMapper; 
	@Resource
	private PmsTerminalMapper terminalMapper;
	@Resource
	private PmsCouponMapper couponMapper;
	@Resource
	private PmsOrgMoneyMapper orgMoneyMapper;
	@Resource
	private PmsOrgMoneyLogMapper orgMoneyLogMapper; 
	
	@Transactional(rollbackFor={RuntimeException.class,Exception.class})
	@Override
	public CouponByVo couponBuy(String mcode,String tcode,String merchantOrderId,Date merchantOrderTime,Long productId, Integer num) {
		CouponByVo couponByVo=new CouponByVo();
		PmsCouponProduct couponProduct= couprProductMapper.selectByPrimaryKey(productId);
		PmsMerchant pmsMerchant=merchantMapper.selectByPrimaryKey(mcode);
		PmsTerminal pmsTerminal=terminalMapper.selectByPrimaryKey(tcode);
		Long currentPrice=couponProduct.getCurrentPrice();
		Long tranPrice=currentPrice*num;
		if(couponProduct==null){
			couponByVo.setReqCode("9999");
			couponByVo.setReqMsg("更具商品ID："+productId+"获取商品失败！");
			return couponByVo;
		}
		if (pmsMerchant==null) {
			couponByVo.setReqCode("8888");
			couponByVo.setReqMsg("更具商户："+mcode+"获取商户信息失败！");
			return couponByVo;
		} 
		if (pmsTerminal==null) {
			couponByVo.setReqCode("7777");
			couponByVo.setReqMsg("更具终端号："+tcode+"获取终端信息失败！");
			return couponByVo;
		} 
		//校验备付金金额是否够不够提示
		PmsOrgMoney pmsOrgMoney=new PmsOrgMoney();
		pmsOrgMoney.setOrgId(pmsMerchant.getOrgId());
		pmsOrgMoney.setMoneyType(0);
		PmsOrgMoney orgMoney=orgMoneyMapper.selectOrgMoney(pmsOrgMoney);
		if (orgMoney.getMoney()<tranPrice) {
			couponByVo.setReqCode("6666");
			couponByVo.setReqMsg("备付金金额:"+orgMoney.getMoney()+"小于购买优惠券总金额："+tranPrice+" 不能进行购买！");
			return couponByVo;
		}
		//获取优惠券
		PmsCoupon pmsCoupon=new PmsCoupon(); 
		pmsCoupon.setProductId(productId);
		pmsCoupon.setStatus(3);
		List<PmsCoupon> pmsCouponList=couponMapper.selectCouponList(pmsCoupon);
		if (pmsCouponList.size()<num) {
			couponByVo.setReqCode("5555");
			couponByVo.setReqMsg("优惠券数量不足:"+pmsCouponList.size()+"小于购买优惠券总数："+num+" 不能进行购买！");
			return couponByVo;
		}
		String ids="";
		for (int i=0;i<num;i++) {
			PmsCoupon pmsCoupon2=pmsCouponList.get(i);
			ids=ids+pmsCoupon2.getCouponId()+",";
		}
		List idsList=Arrays.asList(ids.split(","));
		//----备付金扣减
		PmsOrgMoney upOrgMoney=new PmsOrgMoney();
		upOrgMoney.setMoney(tranPrice);
		upOrgMoney.setOrgId(pmsMerchant.getOrgId());
		upOrgMoney.setMoneyType(0);
		upOrgMoney.setEditTime(new Date());
		int isno=orgMoneyMapper.updateOrgMoney(upOrgMoney);
		//----备付金扣减记录日志
		if(isno>0){
			//----创建优惠券订单
			PmsCouponOrder couponOrder=new PmsCouponOrder();
			 isno=savePmsCouponOrder(couponOrder, pmsMerchant, pmsTerminal, couponProduct, ids, num, merchantOrderId, merchantOrderTime);
			 if(isno>0){
				PmsOrgMoneyLog orgMoneyLog=new PmsOrgMoneyLog();
				orgMoneyLog.setOrgId(pmsMerchant.getOrgId());
				orgMoneyLog.setOrderTypeId(0);
				orgMoneyLog.setTransId(couponOrder.getOrderId());
				orgMoneyLog.setOrderTypeId(couponOrder.getOrderTypeId());
				orgMoneyLog.setBalance(orgMoney.getMoney()-tranPrice);
				orgMoneyLog.setTransMoney(tranPrice);
				orgMoneyLog.setTransFee(0L);
				orgMoneyLog.setIncExp(0);
				//记录备付金日志
				isno=orgMoneyLogMapper.insertSelective(orgMoneyLog);
				if(isno>0){
					//扣商品优惠券数量
					PmsCouponProduct upCouponProduct=new PmsCouponProduct();
					upCouponProduct.setProductId(productId);
					upCouponProduct.setStockNum(num);
					upCouponProduct.setShipmentNum(num);
					isno=couprProductMapper.updateStockNumAndShipmentNum(upCouponProduct);
					if (isno>0) {
						//修改优惠券状态批量修改
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("status", 3);
						map.put("list", idsList);
						isno=couponMapper.batchUpdateStatus(map);
						if (isno>0) {
							couponByVo.setProductId(productId);
							couponByVo.setMcode(mcode);
							couponByVo.setTcode(tcode);
							couponByVo.setMerchantOrderId(merchantOrderId);
							couponByVo.setMerchantOrderTime(merchantOrderTime);
							couponByVo.setNum(num);
							couponByVo.setProductList(toConvertProductList(pmsCouponList,couponProduct));
						} else {
							throw new RuntimeException("修改优惠券状态失败交易回滚");
						}
					
					} else {
						throw new RuntimeException("备付金日志记录失败交易回滚");
					}
				}else{
					 throw new RuntimeException("备付金日志记录失败交易回滚");
				}
			 }else{
				 throw new RuntimeException("创建优惠券失败交易回滚");
			 }
		}else{
			throw new RuntimeException("扣减备付金失败交易回滚");
		}
		
		return couponByVo;
	}
	//----创建优惠券订单购买时
	private  int savePmsCouponOrder(PmsCouponOrder couponOrder,PmsMerchant pmsMerchant,PmsTerminal pmsTerminal,PmsCouponProduct couponProduct,String ids,int num,String merchantOrderId,Date merchantOrderTime){
				
			
				couponOrder.setOrgId(pmsMerchant.getOrgId());
				couponOrder.setClientType(pmsTerminal.getClientType()); //客户端类型
				couponOrder.setMcode(pmsMerchant.getMcode());
				couponOrder.setTcode(pmsTerminal.getTcode());
				couponOrder.setMerchantOrderId(merchantOrderId);
				couponOrder.setMerchantOrderTime(merchantOrderTime);
				couponOrder.setOrderTypeId(880);
				couponOrder.setPayChannelType(800);
				couponOrder.setProductId(couponProduct.getProductId());
				couponOrder.setSellerId(couponProduct.getSellerId());
				couponOrder.setTypeId(couponProduct.getTypeId());
				couponOrder.setSnCode(pmsTerminal.getSnCode());
				couponOrder.setClientInfo(pmsTerminal.getTerminalInfo());
				couponOrder.setRemark(ids);//用来存放优惠券ids
				
				couponOrder.setStatus(3);//等待兑换
				couponOrder.setBankOrderNum("0000");  //上游订单号购买优惠券无没有的都是0000
				couponOrder.setCouponId(0L);
				couponOrder.setCouponCode("0000");
				couponOrder.setFloorPrice(couponProduct.getFloorPrice());
				couponOrder.setCurrentPrice(couponProduct.getCurrentPrice());
				couponOrder.setPrice(couponProduct.getPrice());
				couponOrder.setGoodsTitle(couponProduct.getGoodsTitle());
				couponOrder.setGoodsBody(couponProduct.getGoodsBody());
				couponOrder.setCreateTime(new Date());
				couponOrder.setCreateLongTime(System.currentTimeMillis());
				couponOrder.setReqCode("0");
				couponOrder.setReqMsg("0");
				couponOrder.setNotifyCnt(0);
				couponOrder.setNotifyStatus(1);  //因为不会使用通知顾将其设为1成功状态以后轮询不会查询到
				couponOrder.setEditLongTime(System.currentTimeMillis());
				int isno=couponOrderMapper.insertSelective(couponOrder);
				return isno;
	} 
	
	private  List<CouponVo> toConvertProductList(List<PmsCoupon> pmsCouponList,PmsCouponProduct couponProduct){
		 List<CouponVo> list=new ArrayList<CouponVo>();
		 for (PmsCoupon pmsCoupon : pmsCouponList) {
			 CouponVo vo=new CouponVo();
			 vo.setCouponId(pmsCoupon.getCouponId());
			 vo.setProductName(couponProduct.getProductName());
			 vo.setProductPrice(pmsCoupon.getProductPrice());
			 vo.setCurrentPrice(pmsCoupon.getCurrentPrice());
			 vo.setPrice(pmsCoupon.getPrice());
			 vo.setGoodsTitle(pmsCoupon.getGoodsTitle());
			 vo.setGoodsBody(pmsCoupon.getGoodsBody());
			 vo.setEffectiveTime(DateUtils.toString(pmsCoupon.getEffectiveTime(), "yyyyMMddHHmmss") );
			 vo.setCouponId(pmsCoupon.getCouponId());
			 vo.setCouponCode(pmsCoupon.getCouponCode());
			 vo.setQrCode(pmsCoupon.getQrCode());
			 list.add(vo);
		}
		 return list;
	}
	@Transactional(rollbackFor={RuntimeException.class,Exception.class})
	@Override
	public Map<String,Object> exchangeCoupon(String mcode, String tcode, String merchantOrderId, Date merchantOrderTime,
			Long productId, String couponCode) {
		
		Map<String,Object> ret=new HashMap<String,Object>();
		PmsCouponProduct couponProduct= couprProductMapper.selectByPrimaryKey(productId);
		PmsMerchant pmsMerchant=merchantMapper.selectByPrimaryKey(mcode);
		PmsTerminal pmsTerminal=terminalMapper.selectByPrimaryKey(tcode);
		
		Long currentPrice=couponProduct.getCurrentPrice();
		if(couponProduct==null){
			ret.put("reqCode", "9999");
			ret.put("reqMsg","更具商品ID："+productId+"获取商品失败！");
			return ret;
		}
		if (pmsMerchant==null) {
			ret.put("reqCode", "8888");
			ret.put("reqMsg","更具商户："+mcode+"获取商户信息失败！");
			return ret;
		} 
		if (pmsTerminal==null) {
			ret.put("reqCode", "7777");
			ret.put("reqMsg","更具终端号："+tcode+"获取终端信息失败！");
			return ret;
		} 
		//1更具查询优惠券码是否存在校验状态
		PmsCoupon pmsCoupon=new PmsCoupon(); 
		pmsCoupon.setProductId(productId);
		pmsCoupon.setStatus(3);
		pmsCoupon.setCouponCode(couponCode);
		PmsCoupon coupon=couponMapper.selectCouponByCouponCode(pmsCoupon);
		if (coupon==null) {
			ret.put("reqCode", "5555");
			ret.put("reqMsg","根据优惠券吗查询不到该优惠券！");
			return ret;
		}
		
		//2修改优惠券状态
		PmsCoupon upCoupon=new PmsCoupon();
		upCoupon.setCouponId(coupon.getCouponId());
		upCoupon.setStatus(2);
		int isno=couponMapper.updateByPrimaryKeySelective(upCoupon);
		if (isno>0) {
		//3记录优惠券订单
			PmsCouponOrder couponOrder=new PmsCouponOrder();
			isno=savePmsCouponOrder(couponOrder, pmsMerchant, pmsTerminal, couponProduct, coupon.getCouponId(), merchantOrderId, merchantOrderTime);
			if (isno>0) {
				ret.put("mcode",mcode);
				ret.put("tcode",tcode);
				ret.put("merchantOrderId",merchantOrderId);
				ret.put("merchantOrderTime",DateUtils.formatDate(merchantOrderTime, "yyyyMMddHHmmss"));
				ret.put("productId",productId);
				ret.put("couponCode",couponCode);
				ret.put("reqCode", "0000");
				ret.put("reqMsg","兑换优惠券处理中！");
				ret.put("orderId",couponOrder.getOrderId());
				return ret;
			} else {
				throw new RuntimeException("创建优惠券订单失败兑换优惠券交易回滚");
			}
		} else {
			throw new RuntimeException("修改优惠券状态失败交易回滚");
		}
		
	}
	
	
	//----创建优惠券订单兑换时
	private  int savePmsCouponOrder(PmsCouponOrder couponOrder,PmsMerchant pmsMerchant,PmsTerminal pmsTerminal,PmsCouponProduct couponProduct,Long couponId,String merchantOrderId,Date merchantOrderTime){
					
				
					couponOrder.setOrgId(pmsMerchant.getOrgId());
					couponOrder.setClientType(pmsTerminal.getClientType()); //客户端类型
					couponOrder.setMcode(pmsMerchant.getMcode());
					couponOrder.setTcode(pmsTerminal.getTcode());
					couponOrder.setMerchantOrderId(merchantOrderId);
					couponOrder.setMerchantOrderTime(merchantOrderTime);
					couponOrder.setOrderTypeId(880);
					couponOrder.setPayChannelType(800);
					couponOrder.setProductId(couponProduct.getProductId());
					couponOrder.setSellerId(couponProduct.getSellerId());
					couponOrder.setTypeId(couponProduct.getTypeId());
					couponOrder.setSnCode(pmsTerminal.getSnCode());
					couponOrder.setClientInfo(pmsTerminal.getTerminalInfo());
					couponOrder.setRemark("");//
					
					couponOrder.setStatus(2);//等待兑换
					couponOrder.setBankOrderNum("0000");  //上游订单号购买优惠券无没有的都是0000
					couponOrder.setCouponId(couponId);
					couponOrder.setCouponCode("0000");
					couponOrder.setFloorPrice(couponProduct.getFloorPrice());
					couponOrder.setCurrentPrice(couponProduct.getCurrentPrice());
					couponOrder.setPrice(couponProduct.getPrice());
					couponOrder.setGoodsTitle(couponProduct.getGoodsTitle());
					couponOrder.setGoodsBody(couponProduct.getGoodsBody());
					couponOrder.setCreateTime(new Date());
					couponOrder.setCreateLongTime(System.currentTimeMillis());
					couponOrder.setReqCode("0");
					couponOrder.setReqMsg("0");
					couponOrder.setNotifyCnt(0);
					couponOrder.setNotifyStatus(3);  //
					couponOrder.setEditLongTime(System.currentTimeMillis());
					int isno=couponOrderMapper.insertSelective(couponOrder);
					return isno;
		} 

}
