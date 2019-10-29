package com.qn.rascal.wanandroid.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by Rascal on 2019/10/23.
 */

public class MyApp extends Application {
    //不用单例原因： 系统自己本身就只能创建一个对象，不需要再进行单例处理
    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        //bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel("myChannel"); //设置渠道
        strategy.setAppVersion("1.0.1"); //app的版本
        strategy.setAppPackageName("com.qn.rascal.wanandroid"); //app包名
        CrashReport.initCrashReport(this, "c5e0fdd467", false, strategy);


        /**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调
         * 用初始化接口（如需要使用AndroidManifest.xml中配置好的appkey和channel值，
         * UMConfigure.init调用中appkey和channel参数请置为null）。
         */
        //umeng
        UMConfigure.init(this, "5d8c9a364ca357b3c2000362", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        //58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);

        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        //设置友盟各个平台的appkey
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("1741182341", "97ae49d8cf51c427b58bf98719940535", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }

    public static MyApp getMyApp(){
        return myApp;
    }
}
