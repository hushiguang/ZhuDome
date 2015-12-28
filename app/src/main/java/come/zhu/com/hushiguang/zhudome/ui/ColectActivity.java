package come.zhu.com.hushiguang.zhudome.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseActivity;
import come.zhu.com.hushiguang.zhudome.DB.Colect;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.adapter.ColectAdapter;
import de.greenrobot.event.EventBus;

/**
 * Created by office on 2015/12/9.
 */
public class ColectActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.main_recycle)
    RecyclerView mRecycleView;
    @InjectView(R.id.main_swipe)
    SwipeRefreshLayout mSwipeView;

    @OnClick(R.id.widget_back)
    void goBack() {
        finish();
    }


    @InjectView(R.id.toolbar_linear)
    LinearLayout toolbarLinear;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    ColectActivity mActivity;
    Context mContext;

    private List<Colect> colectList = new ArrayList<>();
    ColectAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colectl);
        ButterKnife.inject(this);
        mActivity = this;
        mContext = this;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
        initView();
    }


    /***
     * 初始化数据和控件信息
     */
    private void initView() {
        toolbarLinear.setVisibility(View.GONE);
        mSwipeView.setOnRefreshListener(this);
        mAdapter = new ColectAdapter(mContext, colectList);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);
        colectList = MyApplication.getInstance().initDB().getColectDao().loadAll();
        mAdapter.setColectList(colectList);

        mAdapter.setOnClickListner(new ColectAdapter.onClickListner() {
            @Override
            public void onItemClick(View v, int postion) {
                EventBus.getDefault().post(colectList.get(postion).getNewsId());
            }
        });
    }


    /***
     * 发出EventBus 来接受跳转的页面
     *
     * @param id
     */
    public void onEventMainThread(String id) {
        Intent intent = new Intent(mContext, StoriesActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 停止刷新
                mSwipeView.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
