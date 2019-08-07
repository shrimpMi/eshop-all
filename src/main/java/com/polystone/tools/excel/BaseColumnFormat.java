package com.polystone.tools.excel;

public interface BaseColumnFormat<T>{
	String format(String col, T t);
}
