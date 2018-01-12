package com.zyzf.polymer.pay.common.core.utils;

import org.apache.commons.lang.StringUtils;

import com.zyzf.polymer.pay.common.core.utils.encrypt.Des3;

public class ClearMac {
	
	public static String start_suffix = "zhongyi支付";
	
	public static String user_suffix = "zyzf_userFFFFFFF";
	
	public static String order_suffix = "zyzf_orderFFFFFF";
	
	public static String co_suffix = "zyzf_coFFFFFFFFF";
	
	public static String bcl_suffix = "zyzf_bclFFFFFFFF";
	
	public static String org_suffix = "zyzf_orgFFFFFFFF";
	
	public static String bro_suffix = "zyzf_broFFFFFFFF";
	
	/**
	 * 用户修改是 算mac  
	 * @param loginName
	 * @param identityCard
	 * @param bankCard
	 * @param openingBank
	 * @param cardholder
	 * @return
	 */
	public static String getUserClearMac(String loginName,String identityCard,String bankCard,String openingBank,String cardholder){
		StringBuffer sb = new StringBuffer();
		if(loginName!=null) loginName = loginName.trim();
		if(identityCard!=null) identityCard = identityCard.trim();
		if(bankCard!=null) bankCard = bankCard.trim();
		if(openingBank!=null) openingBank = openingBank.trim();
		if(cardholder!=null) cardholder = cardholder.trim();
		
		sb.append(loginName).append("|").append(identityCard).append("|").append(bankCard).append("|").append(openingBank).append("|").append(cardholder).append("|").append(user_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 订单结算 mac
	 * @param orderNum
	 * @param userId
	 * @param transMoney
	 * @param bankCard     结算卡号
	 * @param openingBank  结算卡开户行
	 * @param userName     结算卡持卡人姓名
	 * @return
	 */
	public static String getOrderClearMac(String orderNum,Long userId,Double transMoney,String bankCard,String openingBank,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(orderNum).append("|").append(userId).append("|").append(NumberUtil.numFormat(transMoney,"0.00")).append("|").append(bankCard).append("|").append(openingBank).append("|").append(userName).append("|").append(order_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 合单 结算mac
	 * @param loginName
	 * @param transMoney
	 * @param bankCard
	 * @param openingBank
	 * @param cardholder
	 * @return
	 */
	public static String getCoClearMac(String loginName,Double transMoney,String bankCard,String openingBank,String userName,Long batchCoId){
		StringBuffer sb = new StringBuffer();
		sb.append(loginName).append("|").append(NumberUtil.numFormat(transMoney,"0.00")).append("|").append(bankCard).append("|").append(openingBank).append("|").append(userName).append("|").append(batchCoId).append("|").append(co_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 结算日志 结算mac
	 * @param combineOrderId
	 * @param transMoney
	 * @param bankCard
	 * @param openingBank
	 * @param cardholder
	 * @return
	 */
	public static String getBCLClearMac(Long combineOrderId,Double transMoney,String bankCard,String openingBank,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(combineOrderId).append("|").append(NumberUtil.numFormat(transMoney,"0.00")).append("|").append(bankCard).append("|").append(openingBank).append("|").append(userName).append("|").append(bcl_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 机构 结算mac
	 * @param broStatus
	 * @param bankCard
	 * @param openingBank
	 * @param cardholder
	 * @return
	 */
	public static String getOrgClearMac(String bankCard,String openingBank,String cardholder){
		StringBuffer sb = new StringBuffer();
		sb.append(bankCard).append("|").append(openingBank).append("|").append(cardholder).append("|").append(org_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 佣金订单 结算mac
	 * @param organizationId
	 * @param bankCard
	 * @param openingBank
	 * @param cardholder
	 * @return
	 */
	public static String getBroClearMac(Long organizationId,Double totalBrokerage,Double transMoney,String bankCard,String openingBank,String userName){
		StringBuffer sb = new StringBuffer();
		sb.append(organizationId).append("|").append(NumberUtil.numFormat(totalBrokerage,"0.00")).append("|").append(NumberUtil.numFormat(transMoney,"0.00")).append("|").append(bankCard).append("|").append(openingBank).append("|").append(userName).append("|").append(bro_suffix);
		return getMac(sb.toString());
	}
	
	/**
	 * 计算 mac  
	 * 使用 正规MD5 加密
	 * @param str
	 * @return
	 */
	private static String getMac(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		String end_suffix = str.substring(str.lastIndexOf("|")+1);
		String Key = start_suffix+end_suffix;
		return end_suffix+Des3.encode(str,Key);
//		return Des3.encode(str);
	}
	
	/**
	 * 解密
	 * @param str
	 * @param end_suffix
	 * @return
	 */
	public static String dencodeMac(String str){
		try {
			String end_suffix = str.substring(0,16);
			//System.out.println("end_suffix = "+end_suffix);
			String Key = start_suffix+end_suffix;
			//System.out.println("Key = "+Key);
			String eStr = str.substring(16);
			//System.out.println("eStr = "+eStr);
			return Des3.decode(eStr,Key);
//			return Des3.decode(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("612345321321815").append("|").append("中国建设银行").append("|").append("测试").append("|").append(org_suffix);
		String str = getMac(sb.toString());
		System.out.println(str);
		String deStr = dencodeMac(str);
		System.out.println(deStr);
	}
	
}
