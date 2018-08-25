package com.study.nobest.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.baselibrary.imageloader.fresco.FrescoImageConfig;
import com.study.baselibrary.imageloader.fresco.FrescoImageLoaderStrategy;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.R;
import com.study.nobest.recyclerview.viewholder.MyViewHolder;
import com.study.nobest.recyclerview.viewholder.MyViewHolder1;
import com.study.nobest.mvp.model.entity.PoemListInfo;

import java.util.List;
import java.util.Random;

/**
 * Created by jianshengli on 2018/5/1.
 * 多类型的适配器
 *
 */

public class MutilAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PoemListInfo.ListDataEntity> mDatas;
    private Context context;

    //item的类型
    private final static int ITEM_TYPE_HEADER=0;
    private final static int ITEM_TYPE_CONTENT=1;

    public MutilAdapter(List<PoemListInfo.ListDataEntity> mDatas, Context context) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE_HEADER){
            View itemView = View.inflate(context, R.layout.item_recyclerview,null);
            return new MyViewHolder(itemView);
        }else {
            View itemView = View.inflate(context, R.layout.item_recyclerview1,null);
            return new MyViewHolder1(itemView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            SimpleDraweeView img = ((MyViewHolder) holder).simpleDraweeView;
            CommonUtil.getImageLoader().setLoadImgStrategy(new FrescoImageLoaderStrategy()).loadImage(context, FrescoImageConfig.builder().
                    simpleDraweeView(img).url(mDatas.get(position).getUserPicUrl()).build());
        }

        if(holder instanceof MyViewHolder1){
            SimpleDraweeView img = ((MyViewHolder1) holder).simpleDraweeView;
            CommonUtil.getImageLoader().setLoadImgStrategy(new FrescoImageLoaderStrategy()).loadImage(context, FrescoImageConfig.builder().
                    simpleDraweeView(img).url(mDatas.get(position).getUserPicUrl()).build());
        }




    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    Random random = new Random();
    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return ITEM_TYPE_HEADER;
        }else {
            return ITEM_TYPE_CONTENT;
        }
    }
}
