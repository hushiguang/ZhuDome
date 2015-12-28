package come.zhu.com.hushiguang.zhudome.AppBase;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.socks.library.KLog;

/**
 * Created by office on 2015/12/4.
 */
public class BaseActivity extends AppCompatActivity {

    private ActivityTack tack = ActivityTack.getInstanse();

    public Context mContext;
    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        tack.addActivity(this);
        KLog.i(this.getClass().getSimpleName() + ">>>界面创建<<<onCreate");
    }

    @Override
    public void finish() {
        super.finish();
        tack.removeActivity(this);
    }
}
