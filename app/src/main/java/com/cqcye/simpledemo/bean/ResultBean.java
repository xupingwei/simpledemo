package com.cqcye.simpledemo.bean;

import java.util.List;

/**
 * Created by xupingwei on 2016/12/10.
 * 界面需要显示的数据结果集
 */

public class ResultBean {

    private String title;
    private List<DescBean> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DescBean> getRows() {
        return rows;
    }

    public void setRows(List<DescBean> rows) {
        this.rows = rows;
    }
}
