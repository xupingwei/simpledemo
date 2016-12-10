package com.cqcye.simpledemo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.cqcye.simpledemo.adapter.IndexAdapter;
import com.cqcye.simpledemo.bean.DescBean;
import com.cqcye.simpledemo.bean.ResultBean;
import com.cqcye.simpledemo.listener.HttpDoneListener;
import com.cqcye.simpledemo.net.HttpClientUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private IndexAdapter mAdapter;
    private Toolbar toolbar;
    private List<DescBean> descBeens = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        refreshLayout.setRefreshing(true);
        HttpClientUtils.httpGet(this, "", new Object(),
                ResultBean.class, "获取数据",
                new HttpDoneListener() {
                    @Override
                    public void requestSuccess(Object obj, String action) {
                        refreshLayout.setRefreshing(false);
                        ResultBean resultBean = (ResultBean) obj;
                        toolbar.setTitle(resultBean.getTitle());
                        descBeens = resultBean.getRows();
                        mAdapter = new IndexAdapter(MainActivity.this, descBeens);
                        mListView.setAdapter(mAdapter);
                    }

                    @Override
                    public void requestFailed(int failedCode, String failedMessage, String action) {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, failedMessage, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 初始化UI界面
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

    }

}
