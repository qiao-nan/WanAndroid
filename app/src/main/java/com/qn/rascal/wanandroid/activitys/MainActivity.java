package com.qn.rascal.wanandroid.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qn.rascal.wanandroid.R;
import com.qn.rascal.wanandroid.fragments.GuideFragment;
import com.qn.rascal.wanandroid.fragments.HomeFragment;
import com.qn.rascal.wanandroid.fragments.KnowledgeFragment;
import com.qn.rascal.wanandroid.fragments.ProjectFragment;
import com.qn.rascal.wanandroid.fragments.WechatFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNv;
    private DrawerLayout mDl;
    private LinearLayout mLinear;
    /**
     * 友盟
     */
    private Button mShare;
    private TabLayout mTablayout;
    private FragmentManager mFragmentManager;
    private ProjectFragment mProjectFragment;
    private GuideFragment mGuideFragment;
    private WechatFragment mWechatFragment;
    private KnowledgeFragment mKnowledgeFragment;
    private HomeFragment mHomeFragment;
    private RelativeLayout mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Activity中使用leakcanary
        /*RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
        initView();
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNv = (NavigationView) findViewById(R.id.nv);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mFragment = (RelativeLayout) findViewById(R.id.fragment);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mShare = (Button) findViewById(R.id.share);
        //mShare.setOnClickListener(this);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);


        initToolbar(); //初始化 Toolbar
        initFragments(); //初始化fragment

        mTablayout.addTab(mTablayout.newTab().setText("首页").setIcon(R.drawable.selector_icon1));
        mTablayout.addTab(mTablayout.newTab().setText("知识体系").setIcon(R.drawable.selector_icon2));
        mTablayout.addTab(mTablayout.newTab().setText("公众号").setIcon(R.drawable.selector_icon3));
        mTablayout.addTab(mTablayout.newTab().setText("导航").setIcon(R.drawable.selector_icon4));
        mTablayout.addTab(mTablayout.newTab().setText("项目").setIcon(R.drawable.selector_icon5));

        //TabLayout监听，点击不同tab切换不同fragment页面
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        mFragmentManager.beginTransaction().replace(R.id.fragment, mHomeFragment).commit();
                        break;
                    case 1:
                        mFragmentManager.beginTransaction().replace(R.id.fragment, mKnowledgeFragment).commit();
                        break;
                    case 2:
                        mFragmentManager.beginTransaction().replace(R.id.fragment, mWechatFragment).commit();
                        break;
                    case 3:
                        mFragmentManager.beginTransaction().replace(R.id.fragment, mGuideFragment).commit();
                        break;
                    case 4:
                        mFragmentManager.beginTransaction().replace(R.id.fragment, mProjectFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //测试bugly是否可以测出bug（空指针异常）
        /*TextView tv=null;
        tv.setText("111");*/

        /*DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float widthDP = widthPixels / density;

        Log.i("111", "initView: widthPixels"+widthPixels);
        Log.i("111", "initView: density"+density);
        Log.i("111", "initView: widthDP"+widthDP);*/

        //Mytest();
    }

    private void initFragments() {
        mHomeFragment = new HomeFragment();
        mKnowledgeFragment = new KnowledgeFragment();
        mWechatFragment = new WechatFragment();
        mGuideFragment = new GuideFragment();
        mProjectFragment = new ProjectFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.fragment, mHomeFragment).commit();
    }

    /*private void Mytest() {

    }*/

    private void initToolbar() {
        mToolbar.setTitle("首页");
        //设置字体颜色
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //设置标题居中
        mToolbar.setTitleMarginStart(240);
        setSupportActionBar(mToolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mToolbar, R.string.about, R.string.about);
        mDl.addDrawerListener(toggle);
        toggle.syncState();

        //侧滑头布局监听
        View headerView = mNv.getHeaderView(0);
        TextView tv_login = headerView.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转登录页面
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        //侧滑条目监听
        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.item_home:

                        break;
                    case R.id.item_collection:

                        break;
                    case R.id.item_setting:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.item_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                }
                return true;
            }
        });

        //侧滑联动
        mDl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                mLinear.setX((drawerView.getWidth() + 40) * slideOffset);
                mLinear.setY(125 * slideOffset + 30);
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //创建属性菜单

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menu1 = menu.add(1, 1, 1, "");
        //MenuItem menu2 = menu.add(1, 2, 1, "");
        menu1.setIcon(R.drawable.search);
        menu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    //属性菜单监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.share:
                startActivity(new Intent(MainActivity.this, UmengActivity.class));
                break;
        }
    }*/


}
