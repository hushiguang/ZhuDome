package come.zhu.com.hushiguang.zhudome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import come.zhu.com.hushiguang.zhudome.AppBase.FragmentHelper;
import come.zhu.com.hushiguang.zhudome.DB.Themes;
import come.zhu.com.hushiguang.zhudome.DB.ThemesDao;
import come.zhu.com.hushiguang.zhudome.adapter.LeftRecycleAdapter;
import come.zhu.com.hushiguang.zhudome.common.DbHelpe;
import come.zhu.com.hushiguang.zhudome.entity.FragmentEntity;
import come.zhu.com.hushiguang.zhudome.entity.ThemesEntity;
import come.zhu.com.hushiguang.zhudome.ui.ColectActivity;
import come.zhu.com.hushiguang.zhudome.ui.StoriesActivity;
import come.zhu.com.hushiguang.zhudome.ui.fragment.MainFragment;
import come.zhu.com.hushiguang.zhudome.ui.fragment.ThemesFragment;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.left_recycle)
    RecyclerView mRecycleView;

    public LeftRecycleAdapter mAdapter;
    Context mContext;
    public MainActivity mActivity;

    public ArrayList<ThemesEntity.OthersEntity> themesEntities = new ArrayList<>();
    public List<Themes> userThemesEntities = new ArrayList<>();

    @OnClick(R.id.my_photo)
    void goPhoto() {
        startActivity(new Intent(mContext, ColectActivity.class));
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.my_colect)
    void goCloect() {
        startActivity(new Intent(mContext, ColectActivity.class));
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.my_download)
    void goDownload() {
        startActivity(new Intent(mContext, ColectActivity.class));
        drawer.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.home_themes)
    void toMainFragment() {
        EventBus.getDefault().post(new FragmentEntity(new MainFragment()));
        drawer.closeDrawer(GravityCompat.START);
    }

    @InjectView(R.id.toolbar)
    public Toolbar toolbar;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;
    public ThemesDao themesDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mContext = this;
        mActivity = this;


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        /* 初始化数据 **/
        EventBus.getDefault().register(this);
        FragmentHelper.startFragmentAdd(new MainFragment(), mActivity, R.id.main_fragment);
        initView();
        getThemesList();

    }


    private void initView() {
        themesDao = MyApplication.getInstance().initDB().getThemesDao();
        userThemesEntities = themesDao.loadAll();
        mAdapter = new LeftRecycleAdapter(mActivity,mContext, themesEntities, userThemesEntities);
        mAdapter.setUserthemesList(userThemesEntities);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnClickListner(new LeftRecycleAdapter.onClickListner() {
            @Override
            public void onItemClick(View v, int postion) {
                EventBus.getDefault().post(new FragmentEntity(ThemesFragment.newInstance(themesEntities.get(postion))));
                drawer.closeDrawer(GravityCompat.START);
            }

            @Override
            public void onImageViewClick(View v, int postion) {

                if (DbHelpe.getDBWhereId(mActivity, themesEntities.get(postion).getId() + "") == null ||
                        DbHelpe.getDBWhereId(mActivity, themesEntities.get(postion).getId() + "").size() == 0) {
                    Themes themes = new Themes();
                    themes.setDeviceId("admin");
                    themes.setThemesId(themesEntities.get(postion).getId() + "");
                    themesDao.insert(themes);
                } else {

                    themesDao.delete(DbHelpe.getDBWhereId(mActivity, themesEntities.get(postion).getId() + "").get(0));

                    themesEntities.get(postion).setType(0);
                }

                userThemesEntities = themesDao.loadAll();
                mAdapter.setUserthemesList(userThemesEntities);
            }
        });


    }

    /***
     * 发出EventBus 来接受跳转的页面
     *
     * @param fragmentEntity
     */
    public void onEventMainThread(FragmentEntity fragmentEntity) {
        FragmentHelper.startFragmentAdd(fragmentEntity.getFragment(), mActivity, R.id.main_fragment);
    }

    /***
     * 发出EventBus 来接受跳转的页面
     *
     * @param idAndImage
     */
    public void onEventMainThread(String idAndImage) {
        Intent intent = new Intent(mContext, StoriesActivity.class);
        intent.putExtra("id", idAndImage);
        startActivity(intent);
    }


    /***
     * 获取主题信息
     */
    private void getThemesList() {

        MyApplication.getInstance().initNet().getThemes().enqueue(new Callback<ThemesEntity>() {
            @Override
            public void onResponse(Response<ThemesEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    themesEntities = response.body().getOthers();
                    mAdapter.setThemesList(themesEntities);
                } else {
                    Toast.makeText(MainActivity.this, "请求数据为空", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "请求数据错误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            return true;
        } else if (id == R.id.action_message) {

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    /***
     * 监听手机的返回按键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            } else {
                getFragmentManager().popBackStack();
                return false;
            }
        }
        return true;
    }


}
