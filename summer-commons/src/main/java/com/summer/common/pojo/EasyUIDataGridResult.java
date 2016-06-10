package com.summer.common.pojo;

import java.util.List;

/**
 * Created by toy on 6/5/16.
 */
public class EasyUIDataGridResult {
    private long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
