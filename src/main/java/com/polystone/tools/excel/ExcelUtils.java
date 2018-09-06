package com.polystone.tools.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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


	public static void toExcelDowlond(HttpServletResponse resp, String fileName, String title, List<?> list, String[] heads, String[] keys, BaseColumnFormat format) {
		ExcelCreator<?> creator = ExcelUtils.buildCreator(list);
		creator.setFileName(fileName);
		creator.setTitle(title);
		creator.setColTitles(heads);
		creator.setColKeys(keys);
		creator.setFormat(format);
		write(resp,creator);
	}

	public static void write(HttpServletResponse resp,ExcelCreator<?> creator) {
//		没搞清楚,反正从上下文取的response是个**,文件下载成功后会内部跳转一把
//		HttpServletResponse resp = getResp();
		setResponseHeader(resp, creator.getFileName());
		try {
			creator.createAndWrite(resp.getOutputStream());
			resp.getOutputStream().flush();
			//resp.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(),"ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
