package com.polystone.tools.validate;

import com.polystone.tools.common.StringUtil;

public class SVFUtils {
	
	public static String phoneSVF(String phone) {
		if(StringUtil.isTrimEmpty(phone)){
			return phone;
		}
		return phone.replaceAll("(\\d{3})\\d*(\\d{4})","$1****$2");
	}
	
	public static String idcardSVF(String idcard) {
		if(StringUtil.isTrimEmpty(idcard)){
			return idcard;
		}
		return idcard.replaceAll("(\\d{4})\\d*(\\w{4})","$1**********$2");
	}
	
	
	public static String cardNoSVF(String cardNo) {
		if(StringUtil.isTrimEmpty(cardNo)){
			return cardNo;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(cardNo.substring(0, 4));
		for(int i = 0 ; i < cardNo.length()-8 ;i++) {
			sb.append("*");
		}
		sb.append(cardNo.substring(cardNo.length()-4));
		return cardNo.replaceAll("(\\d{4})\\d*(\\w{4})","$1********$2");
	}
	
}
