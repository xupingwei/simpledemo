package com.cqcye.simpledemo.dao;

import com.cqcye.simpledemo.bean.ResultBean;

/**
 * Created by xupingwei on 2016/12/10.
 * 首页数据返回的数据中间层
 */

public class IndexDao {

    /**
     * 获取首页展示的数据
     *
     * @param obj
     * @return
     */
    public ResultBean getIndexData(Object obj) {
        return (ResultBean) obj;
    }
}
