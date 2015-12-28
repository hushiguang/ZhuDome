package come.zhu.com.hushiguang.zhudome.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import come.zhu.com.hushiguang.zhudome.AppBase.BaseActivity;
import come.zhu.com.hushiguang.zhudome.MainActivity;
import come.zhu.com.hushiguang.zhudome.MyApplication;
import come.zhu.com.hushiguang.zhudome.R;
import come.zhu.com.hushiguang.zhudome.entity.GuideEntity;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class GuideActivity extends BaseActivity {

    @InjectView(R.id.guide_image)
    ImageView guideImage;

    @InjectView(R.id.guide_author_name)
    TextView guideAuthorName;

    @InjectView(R.id.guide_app_name)
    TextView guideAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        /* 加载图片 * */
        getGuideImage();
    }


    void getGuideImage() {

        MyApplication.getInstance().initNet().getGuideImage("1080*1776").enqueue(new Callback<GuideEntity>() {
            @Override
            public void onResponse(Response<GuideEntity> response, Retrofit retrofit) {

                if (response.body() != null) {

                    try {
                        guideAuthorName.setText(response.body().getText());
                        guideAppName.setText(getResources().getString(
                                R.string.app_name));
                        Glide.with(mContext).load(response.body().getImg())
                                .into(guideImage);

                        /* 页面的动画效果 **/
                        startAnimotion();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(GuideActivity.this, "获取response失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(GuideActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     * 启动动画效果
     */
    private void startAnimotion() {
        ScaleAnimation aa = new ScaleAnimation(1f, 1.02f, 1f,
                1.05f, 0.5f, 0.5f);
        aa.setDuration(3000);
        guideImage.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }

}
