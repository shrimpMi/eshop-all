package com.polystone.test.gaia;

import java.math.BigDecimal;

/**
 * @Author: Z.K
 * @FileName: GaiaOrderPO
 * @DateTime: 2019/8/26 0026
 * @Version 1.0
 * @Description:
 */
public class GaiaOrderPO {

    private String orderIdOuter;//订单编号
    private BigDecimal amount;      //提现金额，单位：元
    private String memo;      //客户定义的备注说明
    private String workerIdOuter;      //客户定义的用工人员ID
    private String workerName;      //收款方姓名
    private String workerPhoneNo;      //收款方手机号码
    private String workerEmailAddr;      //收款方电子邮件地址，如果没有此项，值为：""
    private String workerPersonId;      //收款方身份证号码，用于二次确认
    private String workerBankName;      //收款方开户行名
    private String workerBankCardNo;      //收款方银行卡卡号
    private String userDataOuter;      //附加数据，在通知接⼝中原样返回，可作为⾃定义参数使⽤，如没有的话，填空：""


    public String getOrderIdOuter() {
        return orderIdOuter;
    }

    public void setOrderIdOuter(String orderIdOuter) {
        this.orderIdOuter = orderIdOuter;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWorkerIdOuter() {
        return workerIdOuter;
    }

    public void setWorkerIdOuter(String workerIdOuter) {
        this.workerIdOuter = workerIdOuter;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhoneNo() {
        return workerPhoneNo;
    }

    public void setWorkerPhoneNo(String workerPhoneNo) {
        this.workerPhoneNo = workerPhoneNo;
    }

    public String getWorkerEmailAddr() {
        return workerEmailAddr;
    }

    public void setWorkerEmailAddr(String workerEmailAddr) {
        this.workerEmailAddr = workerEmailAddr;
    }

    public String getWorkerPersonId() {
        return workerPersonId;
    }

    public void setWorkerPersonId(String workerPersonId) {
        this.workerPersonId = workerPersonId;
    }

    public String getWorkerBankName() {
        return workerBankName;
    }

    public void setWorkerBankName(String workerBankName) {
        this.workerBankName = workerBankName;
    }

    public String getWorkerBankCardNo() {
        return workerBankCardNo;
    }

    public void setWorkerBankCardNo(String workerBankCardNo) {
        this.workerBankCardNo = workerBankCardNo;
    }

    public String getUserDataOuter() {
        return userDataOuter;
    }

    public void setUserDataOuter(String userDataOuter) {
        this.userDataOuter = userDataOuter;
    }
}
