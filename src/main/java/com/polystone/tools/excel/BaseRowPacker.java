package com.polystone.tools.excel;

public interface BaseRowPacker<T>{
	public T getInstance();
	public boolean packing(Object obj, String key, String text);
}
