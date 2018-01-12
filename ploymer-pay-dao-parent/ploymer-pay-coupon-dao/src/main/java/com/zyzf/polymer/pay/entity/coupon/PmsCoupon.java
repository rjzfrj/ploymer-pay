package com.zyzf.polymer.pay.entity.coupon;

import java.util.Date;

public class PmsCoupon {
	private Long couponId;

	private Long productId;

	private Long sellerId;

	private Long typeId;

	private String couponCode;

	private String qrCode;

	private Long floorPrice;

	private Long currentPrice;

	private Long price;

	private String goodsTitle;

	private String goodsBody;

	private Integer effective;

	private Date effectiveTime;

	private Integer status;

	private String createUser;

	private Date createTime;

	private String editorUser;

	private Date editTime;

	private String remark;

	private Date expiredate;

	private Long productPrice;

	private Long orgId;

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode == null ? null : couponCode.trim();
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode == null ? null : qrCode.trim();
	}

	public Long getFloorPrice() {
		return floorPrice;
	}

	public void setFloorPrice(Long floorPrice) {
		this.floorPrice = floorPrice;
	}

	public Long getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Long currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle == null ? null : goodsTitle.trim();
	}

	public String getGoodsBody() {
		return goodsBody;
	}

	public void setGoodsBody(String goodsBody) {
		this.goodsBody = goodsBody == null ? null : goodsBody.trim();
	}

	public Integer getEffective() {
		return effective;
	}

	public void setEffective(Integer effective) {
		this.effective = effective;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEditorUser() {
		return editorUser;
	}

	public void setEditorUser(String editorUser) {
		this.editorUser = editorUser == null ? null : editorUser.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public Long getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Long productPrice) {
		this.productPrice = productPrice;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public String toString() {
		return "PmsCoupon [couponId=" + couponId + ", productId=" + productId + ", sellerId=" + sellerId + ", typeId="
				+ typeId + ", couponCode=" + couponCode + ", qrCode=" + qrCode + ", floorPrice=" + floorPrice
				+ ", currentPrice=" + currentPrice + ", price=" + price + ", goodsTitle=" + goodsTitle + ", goodsBody="
				+ goodsBody + ", effective=" + effective + ", effectiveTime=" + effectiveTime + ", status=" + status
				+ ", createUser=" + createUser + ", createTime=" + createTime + ", editorUser=" + editorUser
				+ ", editTime=" + editTime + ", remark=" + remark + ", expiredate=" + expiredate + ", productPrice="
				+ productPrice + ", orgId=" + orgId + "]";
	}

}