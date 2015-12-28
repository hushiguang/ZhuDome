package come.zhu.com.hushiguang.zhudome.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import come.zhu.com.hushiguang.zhudome.R;

/**
 * 滑动广告
 *
 * @author Gavin
 */
public class AutoViewPager extends FrameLayout {
    private Context mContext = null;
    private boolean isHavaPoint = true;
    private boolean mIsSwitched = false;
    // 存放子视图
    private ArrayList<View> mPageViews = null;
    private ViewGroup mPointsViewGroup = null;
    private ViewPager mViewPager = null;
    private ScrollView mScroll = null;
    private TextView mViewPagerTitle;
    // 图片切换间隔
    private long time = 1500;
    private GuidePageAdapter mGuidePageAdapter = null;
    // 显示轮播的条数
    private int size;
    private ImageView[] mPointViews = null;
    private GuidePageChangeListener mGuidePageChangeListener = null;
    private Timer mTimer = null;

    public AutoViewPagerCallBack autoViewPagerCallBack;

    public SwipeRefreshLayout swipeRefreshLayout = null;

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public AutoViewPager(Context context) {
        super(context);
        mContext = context;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    /**
     * 引入父控件中的ScrollView
     */
    public void setScrollView(ScrollView scrollView) {
        mScroll = scrollView;
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(
                R.layout.widget_auto_looper_viewpager, this, true);
        // 用于存放页面指示器的控件
        mPointsViewGroup = (ViewGroup) findViewById(R.id.viewpager_PointsLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_ViewPager);
        mPointsViewGroup.removeAllViews();
        mViewPager.removeAllViews();
        stopTimer();


        // 防止与ScrollView冲突
        if (swipeRefreshLayout != null) {

            mViewPager.setOnTouchListener(new OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mIsSwitched = false;
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mIsSwitched = true;
                            swipeRefreshLayout.setEnabled(false);
                            swipeRefreshLayout.requestDisallowInterceptTouchEvent(true);

                        /* scroll的 冲突问题 **/
//						mScroll.requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            mIsSwitched = true;
                            swipeRefreshLayout.setEnabled(true);
                            swipeRefreshLayout.requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                    return false;
                }
            });
        }
        mPageViews = new ArrayList<View>();

        for (int m = 0; m < size; m++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_adapter, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.viewpager_image);
            TextView textView = (TextView) view.findViewById(R.id.viewpager_title);
            view.setTag(m);
            autoViewPagerCallBack.setView(view, imageView, textView);
            mPageViews.add(view);
        }

        if (isHavaPoint) {
            mPointViews = new ImageView[size];
            MarginLayoutParams mp = new MarginLayoutParams(
                    15, 15);
            mp.setMargins(5, 0, 5, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mp);
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(mContext);
                mPointViews[i] = imageView;
                if (i == 0) {
                    // 默认选中第一张图片
                    mPointViews[i]
                            .setBackgroundResource(R.mipmap.dot_selected);
                } else {
                    mPointViews[i]
                            .setBackgroundResource(R.mipmap.dot_unselected);
                }

                mPointsViewGroup.addView(mPointViews[i], params);
            }
        }
        mViewPager.setAdapter(getGuidePageAdapter());
        mViewPager.setOnPageChangeListener(getGuidePageChangeListener());
        // 及时通知Adapter数据更新
        mGuidePageAdapter.notifyDataSetChanged();
        // 开始轮播
        if (size > 1) {
            startTimer();
        }

    }

    public interface AutoViewPagerCallBack {
        void setView(View view, ImageView imageView, TextView textView);
    }

    /**
     * @param isHavaPoint
     * @Method isHavaPoint
     * @Description 是否显示页面指示器，请在setSize之前调用
     * @Return void
     */
    public void isHavaPoint(boolean isHavaPoint) {
        this.isHavaPoint = isHavaPoint;
    }

    public void setSize(int size) {
        this.size = size;
        if (size != 0 && autoViewPagerCallBack != null) {
            init();
        }
    }

    class GuidePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if (size < 3)
                return size;
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {

        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            try {
                ((ViewPager) arg0).addView(
                        mPageViews.get(arg1 % mPageViews.size()), 0);
            } catch (Exception e) {

            }
            return mPageViews.get(arg1 % mPageViews.size());
        }

    }


    class GuidePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {

            if (isHavaPoint) {
                arg0 = arg0 % mPageViews.size();
                for (int i = 0; i < mPointViews.length; i++) {
                    mPointViews[arg0]
                            .setBackgroundResource(R.mipmap.dot_selected);
                    if (arg0 != i) {
                        mPointViews[i]
                                .setBackgroundResource(R.mipmap.dot_unselected);
                    }
                }
            }
            mIsSwitched = true;
        }
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer(true);
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, time, time);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mIsSwitched) {
                        mIsSwitched = false;
                    } else {

                        if (size < 3) {
                            int now = mViewPager.getCurrentItem();
                            if (now == 1) {
                                mViewPager.setCurrentItem(0);
                            } else {
                                mViewPager.setCurrentItem(1);
                            }
                        } else {
                            int now = mViewPager.getCurrentItem();
                            mViewPager.setCurrentItem(now + 1, true);
                        }
                    }
            }
        }
    };

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    // 在垃圾回收之前停止计时器
    @Override
    protected void finalize() throws Throwable {
        stopTimer();
    }

    ;

    private GuidePageAdapter getGuidePageAdapter() {
        if (mGuidePageAdapter == null) {
            mGuidePageAdapter = new GuidePageAdapter();
        }
        return mGuidePageAdapter;
    }

    private GuidePageChangeListener getGuidePageChangeListener() {
        if (mGuidePageChangeListener == null) {
            mGuidePageChangeListener = new GuidePageChangeListener();
        }
        return mGuidePageChangeListener;
    }

}