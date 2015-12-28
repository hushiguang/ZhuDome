package come.zhu.com.hushiguang.zhudome.ui.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseFragment;
import come.zhu.com.hushiguang.zhudome.AppBase.FragmentHelper;
import come.zhu.com.hushiguang.zhudome.DB.Colect;
import come.zhu.com.hushiguang.zhudome.DB.StorePrise;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.common.DbHelpe;
import come.zhu.com.hushiguang.zhudome.common.Util;
import come.zhu.com.hushiguang.zhudome.config.Constants;
import come.zhu.com.hushiguang.zhudome.entity.StoriesDetialEntity;
import come.zhu.com.hushiguang.zhudome.entity.StoriesNumberEntity;
import come.zhu.com.hushiguang.zhudome.ui.StoriesActivity;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by office on 2015/12/7.
 */
public class StoriesDetialFragment extends BaseFragment {

    StoriesActivity storiesActivity;
    @InjectView(R.id.detial_img)
    ImageView mImageView;
    @InjectView(R.id.detial_title)
    TextView mTextView;
    @InjectView(R.id.detial_recommend_linear)
    LinearLayout mRecommendLinear;
    @InjectView(R.id.detial_webview)
    WebView mWebView;
    @InjectView(R.id.detial_themes)
    TextView mThemesView;
    @InjectView(R.id.detial_head_linear)
    RelativeLayout mHeadRealaout;
    @InjectView(R.id.recommend_relaout)
    RelativeLayout mRecommendRealaout;
    String htmlBody = "";
    String id;
    String image = "";
    StoriesDetialEntity detialEntity;
    SweetAlertDialog alertDialog;
    StoriesNumberEntity storiesNumberEntity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
            storiesActivity = (StoriesActivity) getActivity();
        }
        alertDialog = Util.showDialog(mContext);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detial, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = (String) bundle.getSerializable("argument");

            if (id.contains(",")) {
                String str[] = id.split(",");
                id = str[0];
                image = str[1];
            }


            alertDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getNumberFormNet(id);
                    getNewsDetialFromNet(id);
                }
            }, 1000);

        }


        if (DbHelpe.getNewsFromDb(storiesActivity, id) == null ||
                DbHelpe.getNewsFromDb(storiesActivity, id).size() == 0) {
            storiesActivity.mColcet.setImageResource(R.mipmap.ic_grade_white_24dp);
        } else {
            storiesActivity.mColcet.setImageResource(R.mipmap.ic_grade_black_24dp);
        }


        storiesActivity.mColcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DbHelpe.getNewsFromDb(storiesActivity, id) == null ||
                        DbHelpe.getNewsFromDb(storiesActivity, id).size() == 0) {
                    Colect colect = new Colect();
                    colect.setDeviceId("admin");
                    colect.setTitle(detialEntity.getTitle());
                    colect.setNewsId(id);
                    if (image != null && !image.equals("")) {
                        colect.setImage(image);
                    }
                    storiesActivity.colectDao.insert(colect);
                    storiesActivity.mColcet.setImageResource(R.mipmap.ic_grade_black_24dp);
                    Toast.makeText(mContext, "已收藏", Toast.LENGTH_SHORT).show();
                } else {
                    storiesActivity.colectDao.delete(DbHelpe.getNewsFromDb(storiesActivity, id).get(0));
                    storiesActivity.mColcet.setImageResource(R.mipmap.ic_grade_white_24dp);
                    Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                }
            }
        });


         /* 看点赞的文章是否在本地数据库里面 显示不同的图片 **/
        if (DbHelpe.getNewsPrise(storiesActivity, id) == null || DbHelpe.getNewsPrise(storiesActivity, id).size() == 0) {
            storiesActivity.mPriseNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_thumb_up_white_18dp, 0, 0, 0);
        } else {
            storiesActivity.mPriseNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_thumb_up_black_18dp, 0, 0, 0);
        }

        final boolean[] flags = {false};
        storiesActivity.mPriseNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = null;
                if (DbHelpe.getNewsPrise(storiesActivity, id) == null || DbHelpe.getNewsPrise(storiesActivity, id).size() == 0) {
                    flags[0] = true;
                    requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "data=1");
                } else {
                    flags[0] = false;
                    requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), "data=0");
                }

                final boolean finalFlags = flags[0];
                MyApplication.getInstance().initNet().storePrise(Constants.Authorization, requestBody, id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                        if (finalFlags) {
                            StorePrise storePrise = new StorePrise();
                            storePrise.setDeviceId("admin");
                            storePrise.setNewsId(id);
                            storiesActivity.storePriseDao.insert(storePrise);
                            storiesActivity.mPriseNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_thumb_up_black_18dp, 0, 0, 0);
                            storiesActivity.mPriseNumber.setText((storiesNumberEntity.getPopularity() + 1) + "");
                        } else {
                            if(DbHelpe.getNewsPrise(storiesActivity, id) != null) {
                                storiesActivity.storePriseDao.delete(DbHelpe.getNewsPrise(storiesActivity, id).get(0));
                            }

                            storiesActivity.mPriseNumber.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_thumb_up_white_18dp, 0, 0, 0);
                            storiesActivity.mPriseNumber.setText((storiesNumberEntity.getPopularity()) + "");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        });


    }


    /***
     * 获取该新闻的数字
     *
     * @param id
     */
    void getNewsDetialFromNet(final String id) {
        MyApplication.getInstance().initNet().getNewsNumber(id).enqueue(new Callback<StoriesNumberEntity>() {
            @Override
            public void onResponse(final Response<StoriesNumberEntity> response, Retrofit retrofit) {
                alertDialog.dismiss();
                if (response.body() != null) {
                    storiesNumberEntity = response.body();
                    /**
                     * 给toolbar设置对应的文本
                     */
                    storiesActivity.mCommentNumber.setText(response.body().getComments() + "");
                    storiesActivity.mPriseNumber.setText((response.body().getPopularity()) + "");
                    storiesActivity.mCommentNumber.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentHelper.startFragmentAdd(CommentFragment.newInstance(response.body(), id), storiesActivity, R.id.news_detial);
                        }
                    });
                } else {
                    Toast.makeText(mContext, "获取response失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(mContext, "网络访问数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * 获取该新闻的内容
     *
     * @param id
     */
    void getNumberFormNet(String id) {


        MyApplication.getInstance().initNet().getNewsDetial(id).enqueue(new Callback<StoriesDetialEntity>() {
            @Override
            public void onResponse(Response<StoriesDetialEntity> response, Retrofit retrofit) {
                if (response.body() != null) {
                    detialEntity = response.body();
                    if (response.body().getImage() != null) {
                        mHeadRealaout.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(response.body().getImage()).into(mImageView);
                        mTextView.setText(response.body().getTitle());
                        mThemesView.setText(response.body().getImage_source());
                    } else {
                        mHeadRealaout.setVisibility(View.GONE);
                    }


                    if (response.body().getRecommenders() != null && response.body().getRecommenders().size() != 0) {
                        mRecommendRealaout.setVisibility(View.VISIBLE);
                        mRecommendLinear.removeAllViews();
                        for (StoriesDetialEntity.RecommendersEntity entity : response.body().getRecommenders()) {
                            final ImageView imageView = new ImageView(mContext);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.height = 80;
                            layoutParams.width = 80;
                            layoutParams.leftMargin = 20;

                            Glide.with(mContext).load(entity.getAvatar()).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imageView.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                            mRecommendLinear.addView(imageView, layoutParams);
                        }
                    } else {
                        mRecommendRealaout.setVisibility(View.GONE);
                    }


                    // 获取web的设置类
                    WebSettings webSettings = mWebView.getSettings();
                    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                    /**
                     * 用WebView显示图片，可使用这个参数 设置网页布局类型：<br>
                     * 1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
                     * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
                     */
                    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    webSettings.setDisplayZoomControls(false);
                    webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setSupportZoom(false);// 用于设置webview放大
                    webSettings.setBuiltInZoomControls(false);
                    mWebView.setBackgroundResource(R.color.transparent);
                    /************************************* 这里是为了调节图片跟屏幕适配 ********************************************/
                    // 使用Jsoup进行读取解析
                    /* 判断是否加载css ***/
                    if (response.body().getCss() != null && response.body().getCss().size() != 0) {
                        for (int i = 0; i < response.body().getCss().size(); i++) {
                            htmlBody += "<link rel=\"stylesheet\" href=\"" + response.body().getCss().get(i) + "\" type=\"text/css\" />" + "</br>";
                        }
                    }
                    htmlBody += response.body().getBody();

                    Document document = Jsoup.parse(htmlBody.trim());
                    Elements firstDivs = document.select("div.headline");
                    firstDivs.remove();
                    // 将所有的图片都设置宽度适应屏幕大小
                    Elements imgElements = document.select("img");
                    // 设置所有图片的大小
                    imgElements.attr("width", "100%");
                    for (int i = 0; i < imgElements.size(); i++) {
                        // 遍历出单个图片节点
                        Element imgElement = imgElements.get(i);
                        // 替换里面的图片路径,公用的服务器地址+数据返回的相对路径
                        if (!imgElement.attr("src").contains("http")
                                && !imgElement.attr("src").contains("base64")) {
                            imgElement.attr("src", imgElement.attr("src"));
                        }
                    }
                    /************************************* 这里是为了调节图片跟屏幕适配 ********************************************/
                    mWebView.loadDataWithBaseURL("", document.html(), "text/html",
                            "UTF-8", null);
                } else {
                    Toast.makeText(mContext, "获取response失败", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(mContext, "网络获取失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onResume() {
        super.onResume();
        initView();
        storiesActivity.mToolBarLinear.setVisibility(View.VISIBLE);
        storiesActivity.titleText.setVisibility(View.GONE);


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
     * @param id
     * @return
     */
    public static StoriesDetialFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("argument", id);
        StoriesDetialFragment contentFragment = new StoriesDetialFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }


}
