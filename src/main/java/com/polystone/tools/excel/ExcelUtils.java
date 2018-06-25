package com.polystone.tools.excel;

import java.io.InputStream;
import java.util.List;


public class ExcelUtils {
	
	
	public static <T> ExcelCreator<T> buildCreator(List<T> list) {
		return new ExcelCreator<T>(list);
	}
	
	public static ExcelParser buildParser(InputStream in) {
		return new ExcelParser(in);
	}
	public static ExcelParser buildParser(InputStream in,Class<?> type,String[] colKeys,BaseRowPacker<?> parser) {
		return new ExcelParser(in, type, colKeys, parser);
	}
	/**
	 * 
	 * @param in
	 * @param type
	 * @param colKeys
	 * @param parser
	 * @param offset 忽略多少行
	 * @return
	 */
	public static ExcelParser buildParser(InputStream in,Class<?> type,String[] colKeys,BaseRowPacker<?> parser,int offset) {
		return new ExcelParser(in, type, colKeys, parser,offset);
	}
	
	
}
