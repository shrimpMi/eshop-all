package com.polystone.tools.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelParser{
	private List<Object> data;
	private Class<?> type;
	private String[] colKeys;
	private BaseRowPacker<?> packer;
	
	private Workbook book;
	private InputStream in;
	private int offset;
	
	public Object instanceObj() {
		if(type==null || type.equals(Map.class)) {
			return new HashMap<String,Object>();
		}else {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<?> parser() {
		//获取Excel文档中的第一个表单
		Sheet sht0 = book.getSheetAt(0);
		//对Sheet中的每一行进行迭代
		data = new ArrayList<Object>();
		//
		if(packer==null) {
			packer = initMapPacker();
		}
		int rowNum = 0;
		for (Row r : sht0) {
			rowNum++;
			if(rowNum > offset) {
				try {
					//创建实体类
					Object obj = packer.getInstance();
					boolean success = true;
					//取出当前行第i个单元格数据，并封装在info实体stuName属性上  
					for(int i = 0 ; i < colKeys.length ; i++) {
						success = packer.packing(obj, colKeys[i], getJavaValue(r.getCell(i)));
						if(success==false)break;
					}
					if(success)
						data.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		 }
		return data;
	}
	
	
	public static String getJavaValue(Cell cell) {
		if (cell==null){
			return null;
		}
		String o;
		int cellType = cell.getCellType();
		switch (cellType) {
			case Cell.CELL_TYPE_BLANK:
				o = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				o = cell.getBooleanCellValue()+"";
				break;
			case Cell.CELL_TYPE_ERROR:
				o = "Bad value!";
				break;
			case Cell.CELL_TYPE_NUMERIC:
				o = cell.getNumericCellValue()+"";
				break;
			case Cell.CELL_TYPE_FORMULA:
				o = cell.getNumericCellValue()+"";
				break;
			default:
				o = cell.getStringCellValue()+"";
		}
		return o;
	}
	
	public BaseRowPacker<Map<String,Object>> initMapPacker() {
		return new BaseRowPacker<Map<String,Object>>() {
			@Override
			public Map<String, Object> getInstance() {
				return new HashMap<String,Object>();
			}
			@Override
			public boolean packing(Object t, String key, String text) {
				((Map<String,Object>)t).put(key, text);
				return true;
			}
		};
	}
	
	public ExcelParser(InputStream in,Class<?> type,String[] colKeys,BaseRowPacker<?> packer) {
		this.in = in;
		this.type = type;
		this.colKeys = colKeys;
		this.packer = packer;
		try {
			this.book = WorkbookFactory.create(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		this.offset=0;
	}
	public ExcelParser(InputStream in,Class<?> type,String[] colKeys,BaseRowPacker<?> packer,int offset) {
		this.in = in;
		this.type = type;
		this.colKeys = colKeys;
		this.packer = packer;
		try {
			this.book = WorkbookFactory.create(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		this.offset=offset;
	}
	public ExcelParser(InputStream in) {
		this.in = in;
		try {
			this.book = WorkbookFactory.create(in);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		this.offset = 0;
	}
	public String[] getColKeys() {
		return colKeys;
	}
	public void setColKeys(String[] colKeys) {
		this.colKeys = colKeys;
	}
	public BaseRowPacker<?> getPacker() {
		return packer;
	}
	public void setPacker(BaseRowPacker<?> packer) {
		this.packer = packer;
	}
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}
}
