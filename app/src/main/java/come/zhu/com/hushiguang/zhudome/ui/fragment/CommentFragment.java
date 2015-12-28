package come.zhu.com.hushiguang.zhudome.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseFragment;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.adapter.LongCommentAdapter;
import come.zhu.com.hushiguang.zhudome.adapter.ShortCommentAdapter;
import come.zhu.com.hushiguang.zhudome.common.MyScrollView;
import come.zhu.com.hushiguang.zhudome.entity.ComnentEntity;
import come.zhu.com.hushiguang.zhudome.entity.StoriesNumberEntity;
import come.zhu.com.hushiguang.zhudome.ui.StoriesActivity;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2015/12/8.
 */
public class CommentFragment extends BaseFragment {

    String id;
    StoriesActivity storiesActivity;

    @InjectView(R.id.long_comment_text)
    TextView mLongCommetSize;
    @InjectView(R.id.no_long_text)
    ImageView mNoLongText;
    @InjectView(R.id.no_long_comment_relat)
    RelativeLayout mLongReLayout;
    @InjectView(R.id.long_comment_recycle)
    RecyclerView mLongRecycle;
    @InjectView(R.id.short_comment_text)
    TextView mShortCommentSize;
    @InjectView(R.id.short_comment_shou)
    TextView mShortCommentFlags;
    @InjectView(R.id.short_comment_recycle)
    RecyclerView mShortRecycle;

    @InjectView(R.id.comment_short_realout)
    RelativeLayout mShortRealayout;

    LongCommentAdapter mLongAdapter;
    ShortCommentAdapter mShortAdapter;
    @InjectView(R.id.comment_scrollview)
    MyScrollView mScrollView;
    boolean isShow;
    private StoriesNumberEntity storiesNumberEntity;

    private ArrayList<ComnentEntity.CommentsEntity> longCommentList = new ArrayList<>();
    private ArrayList<ComnentEntity.CommentsEntity> shortCommentList = new ArrayList<>();


    @OnClick(R.id.comment_short_realout)
    void getShortComment() {

        if (longCommentList.size() == 0) {
            mLongReLayout.setVisibility(View.VISIBLE);
            mLongRecycle.setVisibility(View.GONE);
        }

        if (!isShow) {
            getShortComment(id);
        } else {

            if (mShortRecycle.getVisibility() == View.VISIBLE) {
                mShortRecycle.setVisibility(View.GONE);
                mShortCommentFlags.setText("︾");
                storiesActivity.titleText.setText(storiesNumberEntity.getLong_comments() + "条长评");
            } else {
                mShortRecycle.setVisibility(View.VISIBLE);
                mShortCommentFlags.setText("︽");
                setShortRecycle();
                toScrollLocation();
                storiesActivity.titleText.setText(storiesNumberEntity.getShort_comments() + "条短评");
            }
        }


    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 设置没有长评时候的图片的大小
     */


    void setImageHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        storiesActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams paramms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramms.height = metrics.heightPixels - dip2px(mContext, mLongCommetSize.getHeight());
        mNoLongText.setLayoutParams(paramms);
    }


    void setShortRecycle() {

        if (View.VISIBLE == mShortRecycle.getVisibility()) {
            DisplayMetrics metrics = new DisplayMetrics();
            storiesActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mHeight = mShortRecycle.getMeasuredHeight();
            KLog.d("mHeight " + mHeight + "  heightPixels " + (metrics.heightPixels - mShortRealayout.getMeasuredHeight()));
            if (mHeight < metrics.heightPixels - mShortRealayout.getMeasuredHeight()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.height = metrics.heightPixels - (mShortRealayout.getMeasuredHeight() * 2);
                mShortRecycle.setLayoutParams(params);
            }
        } else {

        }

    }


    /***
     * 让端平滚动到顶部
     */
    void toScrollLocation() {
        mScrollView.post(new Runnable() {
            @Override
            public void run() {

                int[] location = new int[2];//0 是横坐标 1 是纵坐标
                mShortRealayout.getLocationOnScreen(location);
                int offset = 0;
                if (View.VISIBLE == mLongRecycle.getVisibility()) {
                    offset = mLongRecycle.getMeasuredHeight() + mShortRealayout.getMeasuredHeight();
                } else {
                    offset = mLongReLayout.getMeasuredHeight() + mShortRealayout.getMeasuredHeight();
                }

                if (offset < 0) {
                    offset = 0;
                }

                mScrollView.smoothScrollTo(0, offset);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            storiesActivity = (StoriesActivity) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conment, container, false);
        ButterKnife.inject(this, view);
        initView();
        return view;
    }


    void getLongComment(String id) {

        MyApplication.getInstance().initNet().getNewsLongComments(id).enqueue(new Callback<ComnentEntity>() {
            @Override
            public void onResponse(Response<ComnentEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    if (response.body().getComments().size() != 0) {
                        mLongReLayout.setVisibility(View.GONE);
                        mLongRecycle.setVisibility(View.VISIBLE);
                        longCommentList = response.body().getComments();
                        mLongAdapter.setLongCommentList(longCommentList);
                    } else {
                        mLongReLayout.setVisibility(View.VISIBLE);
                        mLongRecycle.setVisibility(View.GONE);
                        setImageHeight();
                    }

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

    void getShortComment(String id) {
        MyApplication.getInstance().initNet().getNewsShortComments(id).enqueue(new Callback<ComnentEntity>() {
            @Override
            public void onResponse(Response<ComnentEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    if (response.body().getComments().size() != 0) {
                        mShortRecycle.setVisibility(View.VISIBLE);
                        shortCommentList = response.body().getComments();
                        mShortAdapter.setShortCommentList(shortCommentList);
                        mShortCommentFlags.setText("︽");
                        toScrollLocation();
                        isShow = true;
                        storiesActivity.titleText.setText(storiesNumberEntity.getShort_comments() + "条短评");


                    } else {
                        mShortRecycle.setVisibility(View.GONE);
                    }


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


    void initView() {
        mLongAdapter = new LongCommentAdapter(mContext, longCommentList);
        mShortAdapter = new ShortCommentAdapter(mContext, shortCommentList);
        mShortRecycle.setItemAnimator(new DefaultItemAnimator());
        mLongRecycle.setItemAnimator(new DefaultItemAnimator());
        mShortRecycle.setLayoutManager(new
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mLongRecycle.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mShortRecycle.setAdapter(mShortAdapter);
        mLongRecycle.setAdapter(mLongAdapter);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
        isShow = false;

        if (bundle != null) {
            storiesNumberEntity = (StoriesNumberEntity) bundle.getSerializable("argument");
            id = (String) bundle.getSerializable("id");
            KLog.d(id);
            KLog.d(storiesNumberEntity);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLongComment(id);
                }
            }, 1000);


            mLongCommetSize.setText(storiesNumberEntity.getLong_comments() + "条长评");
            storiesActivity.titleText.setText(storiesNumberEntity.getLong_comments() + "条长评");
            mShortCommentSize.setText(storiesNumberEntity.getShort_comments() + "条短评");
        }

        storiesActivity.mToolBarLinear.setVisibility(View.GONE);
        storiesActivity.titleText.setVisibility(View.VISIBLE);

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


    /**
     * 传入需要的参数，设置给arguments
     *
     * @param entity
     * @return
     */
    public static CommentFragment newInstance(StoriesNumberEntity entity, String id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("argument", entity);
        bundle.putSerializable("id", id);
        CommentFragment contentFragment = new CommentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


}
