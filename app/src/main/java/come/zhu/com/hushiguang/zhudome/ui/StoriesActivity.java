package come.zhu.com.hushiguang.zhudome.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import come.zhu.com.hushiguang.zhudome.AppBase.FragmentHelper;
import come.zhu.com.hushiguang.zhudome.DB.ColectDao;
import come.zhu.com.hushiguang.zhudome.DB.CommentPriseDao;
import come.zhu.com.hushiguang.zhudome.DB.StorePriseDao;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.entity.FragmentEntity;
import come.zhu.com.hushiguang.zhudome.ui.fragment.StoriesDetialFragment;
import de.greenrobot.event.EventBus;

/**
 * Created by GavinHua on 2015/12/7.
 */
public class StoriesActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    public Toolbar toolbar;
    String id;

    StoriesActivity mActivity;
    Context mContext;
    public ColectDao colectDao;
    public StorePriseDao storePriseDao;
    public CommentPriseDao commentPriseDao;

    @InjectView(R.id.widget_share)
    public ImageView mShare;
    @InjectView(R.id.widget_colect)
    public ImageView mColcet;
    @InjectView(R.id.widget_comment)
    public TextView mCommentNumber;
    @InjectView(R.id.widget_prise)
    public TextView mPriseNumber;
    @InjectView(R.id.toolbar_linear)
    public LinearLayout mToolBarLinear;


    @InjectView(R.id.widget_title)
    public TextView titleText;

    @OnClick(R.id.widget_share)
    void toShare() {
        Toast.makeText(mContext, "toShare", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.widget_colect)
    void toColect() {
        Toast.makeText(mContext, "toColect", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.widget_comment)
    void toComment() {

        Toast.makeText(mContext, "toComment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detial);
        ButterKnife.inject(this);
        mActivity = this;
        mContext = this;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        colectDao = MyApplication.getInstance().initDB().getColectDao();
        storePriseDao = MyApplication.getInstance().initDB().getStorePriseDao();
        commentPriseDao = MyApplication.getInstance().initDB().getCommentPriseDao();
        /* 初始化数据 **/
        EventBus.getDefault().register(this);
        initView();


    }

    private void initView() {

        if (getIntent() != null) {

            id = (String) getIntent().getExtras().get("id");
            FragmentHelper.startFragmentAdd(StoriesDetialFragment.newInstance(id), mActivity, R.id.news_detial);

        }

    }

    /***
     * 发出EventBus 来接受跳转的页面
     *
     * @param fragmentEntity
     */
    public void onEventMainThread(FragmentEntity fragmentEntity) {
        FragmentHelper.startFragmentAdd(fragmentEntity.getFragment(), mActivity, R.id.news_detial);
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


    @OnClick(R.id.widget_back)
    void goBack() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
