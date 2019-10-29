package com.qn.rascal.wanandroid.http;

import android.util.Log;

import com.qn.rascal.wanandroid.app.MyApp;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.qn.rascal.wanandroid.utils.HttpUtils.isNetWorkAvailable;


/**
 * Created by Rascal on 2019/10/23.
 */

public class HttpManager {
    private static HttpManager httpManager;
    //私有化，防止外部通过构造方法创建对象
    private HttpManager(){

    }

    /*volatile 的功能:
    避免编译器将变量缓存在寄存器里
    避免编译器调整代码执行的顺序*/

    //同步锁会影响性能，阻塞别的线程
    //考虑并发性的话，使用静态内部类，不能传参

    public static HttpManager getInstance(){
        if(httpManager==null){
            synchronized (HttpManager.class){
                if(httpManager==null){
                    httpManager=new HttpManager();
                }
            }
        }
        return httpManager;
    }

    private Retrofit getRetrofit(String url){
        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                //会帮我们自动映射成实体类对象
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //使逻辑性更加清晰切换线程简便，可以通过变换符操作，灵活
                .client(getOkhttpClient())
                .build();
        //key是变化的或者是汉字的话，
    }

    public <T> T getService(String url,Class<T> tClass){
        return getRetrofit(url).create(tClass);
    }

    private OkHttpClient getOkhttpClient() {
        MyCacheinterceptor myCacheinterceptor=new MyCacheinterceptor();
        Cache cache=new Cache(new File(MyApp.getMyApp().getCacheDir(),"Cache"),1024*1024*10);
        //日志过滤器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(myCacheinterceptor) //缓存拦截器
                .addNetworkInterceptor(myCacheinterceptor)
                .addInterceptor(httpLoggingInterceptor) //日志拦截器
                //.addInterceptor() //cookie处理
                .cache(cache)
                .build();

    }

    //缓存拦截器
    private class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if (!isNetWorkAvailable(MyApp.getMyApp())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if(isNetWorkAvailable(MyApp.getMyApp())) {
                int maxAge = 0;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            }else{
                int maxStale = 60*60*24*7;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    }

}

