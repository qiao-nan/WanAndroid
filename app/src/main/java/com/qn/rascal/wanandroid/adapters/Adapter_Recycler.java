package com.qn.rascal.wanandroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qn.rascal.wanandroid.R;
import com.qn.rascal.wanandroid.bean.Bean_banner;
import com.qn.rascal.wanandroid.bean.Bean_home;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Rascal on 2019/10/25.
 */

public class Adapter_Recycler extends RecyclerView.Adapter {
    private static final int Type_Banner = 0;
    private static final int Type_List = 1;
    private ArrayList<Bean_home.DataBean.DatasBean> mList;
    private ArrayList<Bean_banner.DataBean> mBanner = new ArrayList<>();
    private Context mContext;

    public Adapter_Recycler(ArrayList<Bean_home.DataBean.DatasBean> list, ArrayList<Bean_banner.DataBean> banner, Context context) {
        mList = list;
        mBanner = banner;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_banner, parent, false);
            return new ViewHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
            return new ViewHolder1(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == Type_Banner) {
            ViewHolder holder1 = (ViewHolder) holder;
            ArrayList<String> imgs = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (int i = 0; i < mBanner.size(); i++) {
                imgs.add(mBanner.get(i).getImagePath());
                titles.add(mBanner.get(i).getTitle());
            }
            holder1.mBannerHome.setBannerTitles(imgs)
                    .setBannerTitles(titles)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    })
                    .start();
        } else {
            ViewHolder1 holder1 = (ViewHolder1) holder;

            holder1.mTvAuthor.setText(mList.get(position).getAuthor());
            holder1.mTvChapterName.setText(mList.get(position).getSuperChapterName()+"/"+mList.get(position).getChapterName());
            holder1.mTvDate.setText(mList.get(position).getNiceDate());
            holder1.mTvTitle.setText(mList.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Type_Banner;
        } else {
            return Type_List;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        Banner mBannerHome;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.mBannerHome = (Banner) view.findViewById(R.id.banner_home);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        View view;
        TextView mTvAuthor;
        TextView mTvChapterName;
        TextView mTvTitle;
        ImageView mIvLove;
        TextView mTvDate;

        ViewHolder1(View view) {
            super(view);
            this.view = view;
            this.mTvAuthor = (TextView) view.findViewById(R.id.tv_author);
            this.mTvChapterName = (TextView) view.findViewById(R.id.tv_ChapterName);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mIvLove = (ImageView) view.findViewById(R.id.iv_love);
            this.mTvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }
}
