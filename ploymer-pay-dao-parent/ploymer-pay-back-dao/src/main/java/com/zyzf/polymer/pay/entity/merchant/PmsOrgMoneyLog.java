package com.zyzf.polymer.pay.entity.merchant;

public class PmsOrgMoneyLog {
    private Long id;

    private Long orgId;

    private Short moneyType;

    private Long transId;

    private Short orderTypeId;

    private Long balance;

    private Short incExp;

    private Long transMoney;

    private Long transFee;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Short getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Short moneyType) {
        this.moneyType = moneyType;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Short getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Short orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Short getIncExp() {
        return incExp;
    }

    public void setIncExp(Short incExp) {
        this.incExp = incExp;
    }

    public Long getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(Long transMoney) {
        this.transMoney = transMoney;
    }

    public Long getTransFee() {
        return transFee;
    }

    public void setTransFee(Long transFee) {
        this.transFee = transFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}