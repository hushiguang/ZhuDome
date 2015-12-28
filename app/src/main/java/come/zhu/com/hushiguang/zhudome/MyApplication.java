package come.zhu.com.hushiguang.zhudome;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.socks.library.KLog;
import com.squareup.okhttp.OkHttpClient;

import come.zhu.com.hushiguang.zhudome.DB.DaoMaster;
import come.zhu.com.hushiguang.zhudome.DB.DaoSession;
import come.zhu.com.hushiguang.zhudome.config.Constants;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by office on 2015/12/4.
 */
public class MyApplication extends Application {
    public static MyApplication myApplication;
    public static Context mContext;
    Retrofit retrofit;
    Constants httpConfig;
    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public SQLiteDatabase db;
    public DaoMaster.DevOpenHelper helper;
    public static boolean isChangeThemes = false;
    protected String DB_NAME = "dome.zhihu.db";


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        myApplication = this;
        KLog.init(BuildConfig.LOG_DEBUG);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
        initNet();

    }


    /***
     * 获取实例
     *
     * @return
     */
    public synchronized static MyApplication getInstance() {
        if (myApplication == null)
            return new MyApplication();
        else
            return myApplication;

    }


    /***
     * 初始化网络信息
     *
     * @return
     */
    public Constants initNet() {
            /* 初始化baseUrl **/
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient();
            client.networkInterceptors().add(new StethoInterceptor());
            retrofit = new Retrofit.Builder().client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://news-at.zhihu.com")
                    .build();
            if (httpConfig == null) {
                httpConfig = retrofit.create(Constants.class);
            }
        } else {
            if (httpConfig == null) {
                httpConfig = retrofit.create(Constants.class);
            } else {
                return httpConfig;
            }
        }
        return httpConfig;
    }


    /***
     * 初始化db信息
     *
     * @return
     */
    public DaoSession initDB() {
        if (daoSession == null) {
            helper = new DaoMaster.DevOpenHelper(mContext, DB_NAME, null);
            getDb();
            getDaoMaster();
            KLog.d(db.getVersion()+"");
            return daoMaster.newSession();
        } else
            return daoSession;
    }


    /**
     * 获取数据库连接
     *
     * @return
     */
    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(db);
        }
        return daoMaster;
    }

    /***
     * 得到当前的db实例
     *
     * @return
     */
    private SQLiteDatabase getDb() {
        if (db == null) {
            db = helper.getWritableDatabase();
        }
        return db;
    }




}
