package com.baidu.tools;

import java.util.List;

/**
 * Created by edwardsbean on 14-10-17.
 */
public class Page {
    private int total;

    public List<ServiceDefine> getRows() {
        return rows;
    }

    public void setRows(List<ServiceDefine> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private List<ServiceDefine> rows;

    @Override
    public String toString() {
        return "Page{" +
                "total='" + total + '\'' +
                ", rows=" + rows +
                '}';
    }
}
