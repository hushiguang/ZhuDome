package come.zhu.com.hushiguang.zhudome.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseFragment;
import come.zhu.com.hushiguang.zhudome.MainActivity;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.adapter.MainViewAdapter;
import come.zhu.com.hushiguang.zhudome.common.Util;
import come.zhu.com.hushiguang.zhudome.entity.HomeMsgEntity;
import de.greenrobot.event.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2015/12/5.
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @InjectView(R.id.main_recycle)
    RecyclerView mRecycle;
    @InjectView(R.id.main_swipe)
    SwipeRefreshLayout mSwipeView;

    MainViewAdapter mAdapter;
    LinearLayoutManager layoutManager;

    /* 是否到底部 **/
    private boolean onLoading;

    int visibleItemCount, totalItemCount, pastItems;

    /*  **/
    private ArrayList<HomeMsgEntity.TopStoriesEntity> listAd = new ArrayList<>();
    private ArrayList<HomeMsgEntity.StoriesEntity> newsList = new ArrayList<>();
    MainActivity mainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null){
            mainActivity = (MainActivity) getActivity();
            mainActivity.toolbar.setTitle("首页");
        }
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        mSwipeView.setOnRefreshListener(this);
        //设置下拉高度
        mSwipeView.setProgressViewEndTarget(true, 400);
        //设置控件大小
        mSwipeView.setSize(SwipeRefreshLayout.DEFAULT);
        return view;
    }


    void initView() {
        getHomeMsgFromNet(false, null);
        mAdapter = new MainViewAdapter(newsList, listAd, mContext);
        mRecycle.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new LinearLayoutManager(mContext);
        mRecycle.setLayoutManager(layoutManager);
        mRecycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MainViewAdapter.onItemClickListener() {
            @Override
            public void onViewPagerOnclick(View v, int postion) {
                KLog.d(listAd.get(postion).getId()+"");
                EventBus.getDefault().post(listAd.get(postion).getId()+"");
            }

            @Override
            public void onItemClick(View v, int postion) {
                if (newsList.get(postion).getImages() == null || newsList.get(postion).getImages().size() == 0){
                    EventBus.getDefault().post(newsList.get(postion).getId()+"");
                }else
                    EventBus.getDefault().post(newsList.get(postion).getId()+"" + "," + newsList.get(postion).getImages().get(0));
            }

        });

        /* recycle的滚动到底部的事件的监听 **/
        mRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastItems = layoutManager.findFirstVisibleItemPosition();

                if (!onLoading) {
                    if ((pastItems + visibleItemCount) >= totalItemCount) {
                        Toast.makeText(mContext, "loading...", Toast.LENGTH_SHORT).show();
                        onLoading = true;
                        getHomeMsgFromNet(true, Util.GetDateAdd(1).split(",")[0]);
                    }
                }
            }
        });


    }


    /***
     * 是否是过往消息
     *
     * @param isBefore
     * @param date
     */
    private void getHomeMsgFromNet(final boolean isBefore, String date) {
        Call<HomeMsgEntity> call;

        if (isBefore)
            call = MyApplication.getInstance().initNet().getHistoryMsg(date);
        else
            call = MyApplication.getInstance().initNet().getHomeMsg();

        call.enqueue(new Callback<HomeMsgEntity>() {
            @Override
            public void onResponse(Response<HomeMsgEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    newsList.addAll(response.body().getStories());
                    mAdapter.setNewsList(newsList);

                    /* 假如是过往消息就不给他重新设值 **/
                    if (!isBefore) {
                        listAd = response.body().getTop_stories();
                        mAdapter.setListAd(listAd);
                    }

                    onLoading = false;
                } else {
                    Toast.makeText(mContext, "获取response失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "获取网络数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        mainActivity.getMenuInflater().inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_message) {
            Toast.makeText(mContext, "通知", Toast.LENGTH_SHORT).show();
            return true;
        }else if(id == R.id.action_settings){
            Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
