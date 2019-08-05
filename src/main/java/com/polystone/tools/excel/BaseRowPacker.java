package com.polystone.tools.excel;

public interface BaseRowPacker<T>{
	T getInstance();
	boolean packing(T obj, String key, String text);
}
