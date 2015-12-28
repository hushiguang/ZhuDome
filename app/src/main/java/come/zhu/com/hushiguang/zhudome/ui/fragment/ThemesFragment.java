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

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseFragment;
import come.zhu.com.hushiguang.zhudome.DB.Themes;
import come.zhu.com.hushiguang.zhudome.MainActivity;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.adapter.ThemesAdapter;
import come.zhu.com.hushiguang.zhudome.common.DbHelpe;
import come.zhu.com.hushiguang.zhudome.entity.ThemesEntity;
import come.zhu.com.hushiguang.zhudome.entity.ThemesNewsMsgEntity;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2015/12/5.
 */
public class ThemesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.main_recycle)
    RecyclerView mRecycleView;
    @InjectView(R.id.main_swipe)
    SwipeRefreshLayout mSwipeView;
    ThemesAdapter mAdapter;
    MainActivity mainActivity;
    ThemesEntity.OthersEntity othersEntity;
    MenuItem item;


    private ArrayList<ThemesNewsMsgEntity.StoriesEntity> newsList = new ArrayList<>();
    private ArrayList<ThemesNewsMsgEntity.EditorsEntity> editorList = new ArrayList<>();
    ThemesNewsMsgEntity entity = new ThemesNewsMsgEntity();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            mainActivity = (MainActivity) getActivity();
        }
        initView();
    }

    void initView() {

        mAdapter = new ThemesAdapter(newsList, editorList, entity, mContext);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ThemesAdapter.onItemClickListener() {

            @Override
            public void onItemEditorClick(View v) {
                Toast.makeText(mContext, "作者", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImageViewClick(View v) {
                Toast.makeText(mContext, "tupian", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(View v, int postion) {
                if (newsList.get(postion).getImages() == null || newsList.get(postion).getImages().size() == 0) {
                    EventBus.getDefault().post(newsList.get(postion).getId() + "");
                } else
                    EventBus.getDefault().post(newsList.get(postion).getId() + "" + "," + newsList.get(postion).getImages().get(0));
            }
        });

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


    /**
     * 请求网络数据
     *
     * @param id
     */
    private void getThemesList(String id) {
        MyApplication.getInstance().initNet().getThemesMsgList(id).enqueue(new Callback<ThemesNewsMsgEntity>() {
            @Override
            public void onResponse(Response<ThemesNewsMsgEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    mAdapter.setEntity(response.body());
                    newsList = response.body().getStories();
                    mAdapter.setNewsList(newsList);

                    if (response.body().getEditors() != null && response.body().getEditors().size() != 0) {
                        editorList = response.body().getEditors();
                        mAdapter.seteditorList(editorList);
                    }

                } else {
                    Toast.makeText(mContext, "请求respons失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 传入需要的参数，设置给arguments
     *
     * @param entity
     * @return
     */

    public static ThemesFragment newInstance(ThemesEntity.OthersEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("argument", entity);
        ThemesFragment contentFragment = new ThemesFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
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
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        if (bundle != null) {
            othersEntity = (ThemesEntity.OthersEntity) bundle.getSerializable("argument");
            mainActivity.toolbar.setTitle(othersEntity.getName());
        }

        if (newsList.size() != 0) {
            newsList.clear();
            editorList.clear();
        }

        getThemesList(othersEntity.getId() + "");

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        mainActivity.getMenuInflater().inflate(R.menu.other_fragment_menu, menu);
        item = menu.findItem(R.id.action_add);

        if (DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "") == null ||
                DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "").size() == 0) {
            item.setIcon(R.mipmap.ic_add_circle_outline_white_24dp);
        } else {
            item.setIcon(R.mipmap.ic_remove_circle_outline_white_24dp);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            if (DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "") == null ||
                    DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "").size() == 0) {
                Themes themes = new Themes();
                themes.setDeviceId("admin");
                themes.setThemesId(othersEntity.getId() + "");
                mainActivity.themesDao.insert(themes);
                item.setIcon(R.mipmap.ic_remove_circle_outline_white_24dp);
            } else {
                String themesId = DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "").get(0).getThemesId();
                for (int i = 0; i < mainActivity.themesEntities.size(); i++) {
                    if (themesId.equals(mainActivity.themesEntities.get(i).getId() + "")) {
                        mainActivity.themesEntities.get(i).setType(0);
                        mainActivity.themesDao.delete(DbHelpe.getDBWhereId(mainActivity, othersEntity.getId() + "").get(0));
                    }
                }

                item.setIcon(R.mipmap.ic_add_circle_outline_white_24dp);
            }

            mainActivity.userThemesEntities = mainActivity.themesDao.loadAll();
            mainActivity.mAdapter.setUserthemesList(mainActivity.userThemesEntities);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
