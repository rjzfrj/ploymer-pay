package com.zyzf.polymer.pay.entity.coupon;

import java.util.Date;

public class PmsCouponOrder {
    private Long orderId;

    private Long orgId;

    private Integer clientType;

    private String mcode;

    private String tcode;

    private String merchantOrderId;

    private Date merchantOrderTime;

    private Integer orderTypeId;

    private Integer payChannelType;

    private Long productId;

    private Long sellerId;

    private Long typeId;

    private String ip;

    private String snCode;

    private String clientInfo;

    private String remark;

    private Integer status;

    private String bankOrderNum;

    private Long couponId;

    private String qrcodeUrl;

    private String couponCode;

    private Long floorPrice;

    private Long currentPrice;

    private Long price;

    private String goodsTitle;

    private String goodsBody;

    private String thirdOrderNum;

    private Date createTime;

    private Long createLongTime;

    private Date editTime;

    private Long editLongTime;

    private Date payDate;

    private Long payLongTime;

    private String merchantId;

    private String terminalId;

    private String reqCode;

    private String reqMsg;

    private String notifyUrl;

    private Integer notifyCnt;

    private Integer notifyStatus;

    private Date callbackDate;

    private Long callbackLongTime;

    private Integer isInvestig;

    private Integer markedRed;

    private String description;

    private Long maori;

    private Long brokerage;

    private String otherParam;

    private Long productPrice;
    
     //添加参数
    private Date effectiveTime;
    
    private String productName;
    
    public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode == null ? null : mcode.trim();
    }

    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode == null ? null : tcode.trim();
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId == null ? null : merchantOrderId.trim();
    }

    public Date getMerchantOrderTime() {
        return merchantOrderTime;
    }

    public void setMerchantOrderTime(Date merchantOrderTime) {
        this.merchantOrderTime = merchantOrderTime;
    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Integer getPayChannelType() {
        return payChannelType;
    }

    public void setPayChannelType(Integer payChannelType) {
        this.payChannelType = payChannelType;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode == null ? null : snCode.trim();
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo == null ? null : clientInfo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBankOrderNum() {
        return bankOrderNum;
    }

    public void setBankOrderNum(String bankOrderNum) {
        this.bankOrderNum = bankOrderNum == null ? null : bankOrderNum.trim();
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
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

    public String getThirdOrderNum() {
        return thirdOrderNum;
    }

    public void setThirdOrderNum(String thirdOrderNum) {
        this.thirdOrderNum = thirdOrderNum == null ? null : thirdOrderNum.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateLongTime() {
        return createLongTime;
    }

    public void setCreateLongTime(Long createLongTime) {
        this.createLongTime = createLongTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Long getEditLongTime() {
        return editLongTime;
    }

    public void setEditLongTime(Long editLongTime) {
        this.editLongTime = editLongTime;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getPayLongTime() {
        return payLongTime;
    }

    public void setPayLongTime(Long payLongTime) {
        this.payLongTime = payLongTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId == null ? null : terminalId.trim();
    }

    public String getReqCode() {
        return reqCode;
    }

    public void setReqCode(String reqCode) {
        this.reqCode = reqCode == null ? null : reqCode.trim();
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg == null ? null : reqMsg.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public Integer getNotifyCnt() {
        return notifyCnt;
    }

    public void setNotifyCnt(Integer notifyCnt) {
        this.notifyCnt = notifyCnt;
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public Date getCallbackDate() {
        return callbackDate;
    }

    public void setCallbackDate(Date callbackDate) {
        this.callbackDate = callbackDate;
    }

    public Long getCallbackLongTime() {
        return callbackLongTime;
    }

    public void setCallbackLongTime(Long callbackLongTime) {
        this.callbackLongTime = callbackLongTime;
    }

    public Integer getIsInvestig() {
        return isInvestig;
    }

    public void setIsInvestig(Integer isInvestig) {
        this.isInvestig = isInvestig;
    }

    public Integer getMarkedRed() {
        return markedRed;
    }

    public void setMarkedRed(Integer markedRed) {
        this.markedRed = markedRed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getMaori() {
        return maori;
    }

    public void setMaori(Long maori) {
        this.maori = maori;
    }

    public Long getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Long brokerage) {
        this.brokerage = brokerage;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam == null ? null : otherParam.trim();
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }
}