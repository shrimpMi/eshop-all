package com.polystone.test.gaia;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页类
 *
 * @author jimmy
 * @version V1.0, 2017/8/10
 * @copyright
 */
public class PageDTO<T> implements Serializable {

    private int pageNum;
    private int pageSize;
    private long total;
    private List<T> list;
    private List<?> attrs;

    public PageDTO() {
    }

    /**
     * 构造
     */
    public PageDTO(int pageNum, int pageSize, long total, List<T> infos) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = infos;
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }


}
