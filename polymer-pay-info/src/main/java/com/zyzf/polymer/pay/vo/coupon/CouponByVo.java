package com.zyzf.polymer.pay.vo.coupon;

import java.util.Date;
import java.util.List;

public class CouponByVo {
	
	private String action;
	private String mcode;
	private String tcode;
	private String version;

	private String merchantOrderId;
	private Date merchantOrderTime;
	private Long productId;
	private Integer num;

	private String reqCode;
	private String reqMsg;
	private List<CouponVo> productList;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getMcode() {
		return mcode;
	}
	public void setMcode(String mcode) {
		this.mcode = mcode;
	}
	public String getTcode() {
		return tcode;
	}
	public void setTcode(String tcode) {
		this.tcode = tcode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public Date getMerchantOrderTime() {
		return merchantOrderTime;
	}
	public void setMerchantOrderTime(Date merchantOrderTime) {
		this.merchantOrderTime = merchantOrderTime;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getReqCode() {
		return reqCode;
	}
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
	public String getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
	public List<CouponVo> getProductList() {
		return productList;
	}
	public void setProductList(List<CouponVo> productList) {
		this.productList = productList;
	}
	
	
	


}
