package com.qn.rascal.wanandroid.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qn.rascal.wanandroid.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

public class UmengActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 带面板分享
     */
    private Button mBtnShareBoard;
    /**
     * qq分享
     */
    private Button mBtnQq;
    /**
     * 微信分享
     */
    private Button mBtnWechat;
    /**
     * 新浪分享
     */
    private Button mBtnSina;
    /**
     * qq登录
     */
    private Button mBtnQqLogin;
    /**
     * 微信登录
     */
    private Button mBtnWechatLogin;
    /**
     * 新浪登录
     */
    private Button mBtnSinaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umeng);
        initView();
        initPer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mBtnShareBoard = (Button) findViewById(R.id.btn_share_board);
        mBtnShareBoard.setOnClickListener(this);
        mBtnQq = (Button) findViewById(R.id.btn_qq);
        mBtnQq.setOnClickListener(this);
        mBtnWechat = (Button) findViewById(R.id.btn_wechat);
        mBtnWechat.setOnClickListener(this);
        mBtnSina = (Button) findViewById(R.id.btn_sina);
        mBtnSina.setOnClickListener(this);
        mBtnQqLogin = (Button) findViewById(R.id.btn_qq_login);
        mBtnQqLogin.setOnClickListener(this);
        mBtnWechatLogin = (Button) findViewById(R.id.btn_wechat_login);
        mBtnWechatLogin.setOnClickListener(this);
        mBtnSinaLogin = (Button) findViewById(R.id.btn_sina_login);
        mBtnSinaLogin.setOnClickListener(this);
    }

    private void initPer() {
        //1.清单文件配置
        //2检测权限,没有,第3步
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            //3.申请权限
            String[] pers = {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, pers, 100);
        }
        //4.处理了申请结果
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户授权了
            } else {
                //用户拒绝了
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_share_board:
                shareBoard();
                break;
            case R.id.btn_qq:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_wechat:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_sina:
                share(SHARE_MEDIA.SINA);
                break;
            case R.id.btn_qq_login:
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.btn_wechat_login:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.btn_sina_login:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }

    //第三方登录
    private void login(SHARE_MEDIA type) {
        UMShareAPI.get(this).getPlatformInfo(this, type, authListener);
    }
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action,
                               Map<String, String> data) {
            logMap(data);
            showToast("成功");

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            showToast("失败");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            showToast("取消");
        }
    };

    private void logMap(Map<String, String> data) {
        //entrySet keyset
        for (Map.Entry<String, String> entry :data.entrySet()) {
            //System.out.println(entry.getKey()+":"+entry.getValue());
            Log.i("111", "logMap: "+entry.getKey()+":"+entry.getValue());
        }
    }


    //不带面板分享
    private void share(SHARE_MEDIA type) {
        UMImage image = new UMImage(this, "http://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg");//网络图片
        new ShareAction(UmengActivity.this)
                .setPlatform(type)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }




    private void shareBoard() {
        UMImage image = new UMImage(this, "https://gitee.com/ccyy2019/server/raw/master/img01.png");
        new ShareAction(UmengActivity.this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .withMedia(image) //分享图片
                .open();

    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            showToast("成功了");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            showToast("失败");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            showToast("取消");
        }
    };
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //删除授权
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.QQ, authListener);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.SINA, authListener);

    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}