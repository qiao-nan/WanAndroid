package com.qn.rascal.wanandroid.contract;


import com.qn.rascal.wanandroid.bean.Bean;
import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;

import java.util.List;


public interface MainContract {
    interface MainView{
        void getListData(Bean_home beans);
        void getBannerData(Bean_banner bean_banners);
        void onError(String error);
    }

    interface MainModuel{
        interface MainCallBack{
            void setListData(Bean_home beans);
            void setBannerData(Bean_banner bean_banners);
            void onError(String error);
        }
        void getData(MainCallBack mainCallBack);
    }

    interface MainPresenter{
        void getData();
    }
}
