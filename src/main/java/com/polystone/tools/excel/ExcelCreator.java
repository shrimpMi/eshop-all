package com.polystone.tools.excel;

import com.polystone.tools.common.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExcelCreator<T> {

    private List<T> list;
    private String title;
    private String fileName;
    private String[] colKeys;
    private String[] colTitles;
    private BaseColumnFormat<T> format;

    private HSSFWorkbook book;
    private OutputStream out;

    private void create() throws IOException {
        createSheet("Sheet 1", 0, title, list, colKeys, colTitles, format);
    }

    //把创建的内容写入到输出流中，并关闭输出流
    private void wirte() throws IOException {
        if (book != null) {
            book.write(out);
        }
    }

    public void createAndWrite(OutputStream outputStream) {
        this.out = outputStream;
        try {
            create();
            wirte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSheet(String name, int index, String titleName, List<T> list, String[] colKeys,
        String[] colHeads, BaseColumnFormat<T> format) throws IOException {
        //创建工作薄
        if (book == null) {
            book = new HSSFWorkbook();
        }
        //创建新的一页
        HSSFSheet sheet = book.createSheet(name);
        int rownum = 0;
        //构造表头
        if (StringUtil.isNotEmpty(titleName)) {
            createTitle(sheet, titleName, 0, 0, 0, colHeads.length - 1);
            rownum++;
        }
        createHead(sheet, colHeads, rownum);
        rownum++;
        createBody(sheet, list, colKeys, rownum, format);

    }


    private void createBody(HSSFSheet sheet, List<T> list, String[] colKeys, int rownum, BaseColumnFormat<T> format) {
        // 将查询出的数据设置到sheet对应的单元格中
        HSSFCellStyle style = getStyle(); // 单元格样式对象
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);// 遍历每个对象
            HSSFRow row = sheet.createRow(i + rownum);// 创建所需的行数（从第三行开始写数据）
            for (int j = 0; j < colKeys.length; j++) {
                // 设置单元格的数据类型
                HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(format.format(colKeys[j], t));
                cell.setCellStyle(style); // 设置单元格样式
            }
        }
    }

    private void createHead(HSSFSheet sheet, String[] heads, int rownum) {
        // 创建行
        HSSFRow row = sheet.createRow(rownum); // 在索引2的位置创建行(最顶端的行开始的第二行)
        // 将列头设置到sheet的单元格中
        for (int i = 0; i < heads.length; i++) {
            HSSFCell col = row.createCell(i); // 创建列头对应个数的单元格
            col.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
            HSSFRichTextString text = new HSSFRichTextString(heads[i]);
            col.setCellValue(text); // 设置列头单元格的值
            col.setCellStyle(getColumnTopStyle()); // 设置列头单元格样式
        }
    }

    private void createTitle(HSSFSheet sheet, String title2, int srow, int erow, int scol, int ecol) {
        // 产生表格标题行
        HSSFRow rowm = sheet.createRow(0);
        HSSFCell cellTiltle = rowm.createCell(0);
        // sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
        HSSFCellStyle style = getStyle(); // 单元格样式对象
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(srow, erow, scol, ecol));// 列行
        cellTiltle.setCellStyle(style);
        cellTiltle.setCellValue(title);
    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle() {

        // 设置字体
        HSSFFont font = book.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        // 设置字体名字
//        font.setFontName("Courier New");
//        // 设置样式;
        HSSFCellStyle style = book.createCellStyle();
//        // 设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        // 设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        // 设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        // 设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        // 设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        // 设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        // 设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        // 设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        // 在样式用应用设置的字体;
//        style.setFont(font);
//        // 设置自动换行;
//        style.setWrapText(false);
//        // 设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
// 
        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getStyle() {
        // 设置字体
        HSSFFont font = book.createFont();
        // 设置字体大小
        // font.setFontHeightInPoints((short)10);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = book.createCellStyle();
        // 设置底边框;
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        // 设置底边框颜色;
//        style.setBottomBorderColor(HSSFColor.BLACK.index);
//        // 设置左边框;
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        // 设置左边框颜色;
//        style.setLeftBorderColor(HSSFColor.BLACK.index);
//        // 设置右边框;
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        // 设置右边框颜色;
//        style.setRightBorderColor(HSSFColor.BLACK.index);
//        // 设置顶边框;
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        // 设置顶边框颜色;
//        style.setTopBorderColor(HSSFColor.BLACK.index);
//        // 在样式用应用设置的字体;
//        style.setFont(font);
//        // 设置自动换行;
//        style.setWrapText(false);
//        // 设置水平对齐的样式为居中对齐;
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        // 设置垂直对齐的样式为居中对齐;
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    public ExcelCreator() {
    }

    public ExcelCreator(List<T> list) {
        this.list = list;
    }

    public void setData(List<T> list) {
        this.list = list;
    }

    public void addData(T t) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        list.add(t);
    }

    public void addData(List<T> list) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        list.addAll(list);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName != null && fileName.length() > 0 && !fileName.endsWith(".xls")) {
            fileName = fileName + ".xls";
        }
        this.fileName = fileName;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String[] getColKeys() {
        return colKeys;
    }

    public void setColKeys(String[] colKeys) {
        this.colKeys = colKeys;
    }

    public String[] getColTitles() {
        return colTitles;
    }

    public void setColTitles(String[] colTitles) {
        this.colTitles = colTitles;
    }

    public BaseColumnFormat<T> getFormat() {
        return format;
    }

    public void setFormat(BaseColumnFormat<T> format) {
        this.format = format;
    }

}
