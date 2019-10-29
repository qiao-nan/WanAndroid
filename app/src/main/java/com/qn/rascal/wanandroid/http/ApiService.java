package com.qn.rascal.wanandroid.http;

import com.qn.rascal.wanandroid.bean.Bean;
import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Rascal on 2019/10/23.
 */

public interface ApiService {

    // http://www.wanandroid.com/article/list/0/json
    String BaseURL = "https://www.wanandroid.com/";

    @GET("article/list/0/json")
    Observable<BaseResponse<List<Bean_home>>> getHomeJson();


    //https://www.wanandroid.com/banner/json
    @GET("banner/json")
    Observable<BaseResponse<List<Bean_banner>>> getBannerJson();

}
