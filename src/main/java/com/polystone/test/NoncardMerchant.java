/*
 * Powered By [jimmy]
 * Web Site:
 * Google Code:
 * Since 2017 - 2018
 */

package com.polystone.test;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author    jimmy
 * @version   V1.0, 2018-11-19
 * @since     [产品/模块版本]
 */
public class NoncardMerchant implements Serializable {
	
    /**
     * id       db_column: id 
     */	
	private String id;
    /**
     * 代理商手机号       db_column: phone 
     */	
	private String phone;
    /**
     * 子代理账户号       db_column: certificate_son_mail 
     */	
	private String certificateSonMail;
    /**
     * 联系电话       db_column: certificate_mobile 
     */	
	private String certificateMobile;
    /**
     * 瀚银商户号       db_column: hp_mer_code 
     */	
	private String hpMerCode;
    /**
     * 商户名称       db_column: mer_name 
     */	
	private String merName;
	/**
	 * 用户名       db_column: hp_user_name 
	 */	
	private String hpUserName;
    /**
     * 创建时间       db_column: create_time 
     */	
	private Date createTime;
    /**
     * 00审核通过，01未提交资料，02资料审核中，03审核未通过       db_column: audit_status 
     */	
	private String auditStatus;
	/**
	 * 用户等级 
	 */	
	private String hpMerLv;

	public NoncardMerchant(){
	}
	
	
	public static NoncardMerchant build(Map<String,String> info) {
		//"certificateSonMail","hpMerCode","merName","merStatus","merMobile","merCreateTime","productCode"
		NoncardMerchant it = new NoncardMerchant();
		it.setId("1111");
		it.setCertificateSonMail(info.get("certificateSonMail"));
		it.setCertificateMobile(info.get("merMobile"));
		it.setHpMerCode(info.get("hpMerCode"));
		it.setMerName(info.get("merName"));
		it.setHpUserName(info.get("hpUserName"));
		String merStatus = info.get("merStatus");//00审核通过，01未提交资料，02资料审核中，03审核未通过
		if("审核通过".equals(merStatus)) {
			it.setAuditStatus("00");
		}else if("未提交资料".equals(merStatus)) {
			it.setAuditStatus("01");
		}else if("资料审核中".equals(merStatus)) {
			it.setAuditStatus("02");
		}else if("审核未通过".equals(merStatus)) {
			it.setAuditStatus("03");
		}else {
			it.setAuditStatus("03");
		}
		String time = info.get("merCreateTime");
		if(time!=null && time.length() > 0) {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				it.setCreateTime(df.parse(time.trim()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return it;
	}
	
	public NoncardMerchant(
		String id
	){
		this.id = id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setCertificateSonMail(String certificateSonMail) {
		this.certificateSonMail = certificateSonMail;
	}
	
	public String getCertificateSonMail() {
		return this.certificateSonMail;
	}
	public void setCertificateMobile(String certificateMobile) {
		this.certificateMobile = certificateMobile;
	}
	
	public String getCertificateMobile() {
		return this.certificateMobile;
	}
	public void setHpMerCode(String hpMerCode) {
		this.hpMerCode = hpMerCode;
	}
	
	public String getHpMerCode() {
		return this.hpMerCode;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	
	public String getMerName() {
		return this.merName;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}

	public String getHpMerLv() {
		return hpMerLv;
	}

	public void setHpMerLv(String hpMerLv) {
		this.hpMerLv = hpMerLv;
	}


	public String getHpUserName() {
		return hpUserName;
	}


	public void setHpUserName(String hpUserName) {
		this.hpUserName = hpUserName;
	}

	@Override
	public String toString() {
		return "NoncardMerchant{" +
				"id='" + id + '\'' +
				", phone='" + phone + '\'' +
				", certificateSonMail='" + certificateSonMail + '\'' +
				", certificateMobile='" + certificateMobile + '\'' +
				", hpMerCode='" + hpMerCode + '\'' +
				", merName='" + merName + '\'' +
				", hpUserName='" + hpUserName + '\'' +
				", createTime=" + createTime +
				", auditStatus='" + auditStatus + '\'' +
				", hpMerLv='" + hpMerLv + '\'' +
				'}';
	}
}

