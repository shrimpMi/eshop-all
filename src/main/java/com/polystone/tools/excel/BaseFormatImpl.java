package com.polystone.tools.excel;


public class BaseFormatImpl<T> implements BaseColumnFormat<T>{

	@Override
	public String format(String col, Object t) {
		return BeanUtils.getAttrValue(t,col);
	}

}
