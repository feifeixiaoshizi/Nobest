package com.study.nobest.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.baselibrary.imageloader.fresco.FrescoImageConfig;
import com.study.baselibrary.imageloader.fresco.FrescoImageLoaderStrategy;
import com.study.baselibrary.utils.CommonUtil;
import com.study.nobest.R;
import com.study.nobest.recyclerview.viewholder.MyViewHolder;
import com.study.nobest.mvp.model.entity.PoemListInfo;

import java.util.List;

/**
 * Created by jianshengli on 2018/4/30.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static  String TAG = "MyAdapter";
    private List<PoemListInfo.ListDataEntity> mDatas;
    private Context context;

    public MyAdapter(List<PoemListInfo.ListDataEntity> mDatas,Context context) {
        this.mDatas = mDatas;
        this.context= context;

    }

    public List<PoemListInfo.ListDataEntity> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<PoemListInfo.ListDataEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent!=null){
            Log.d(TAG,"parent:"+parent.toString()+"parentName:"+parent.getClass().getSimpleName()+"viewType:"+viewType);
        }

        View  itemView = View.inflate(context, R.layout.item_recyclerview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        //viewClassName:LinearLayoutviewHeight:0viewHeight:0
        Log.d(TAG,"viewClassName:"+view.getClass().getSimpleName()+"viewHeight:"+view.getWidth()+"viewHeight:"+view.getHeight());

        if(holder instanceof MyViewHolder){
            SimpleDraweeView img = ((MyViewHolder) holder).simpleDraweeView;
            CommonUtil.getImageLoader().setLoadImgStrategy(new FrescoImageLoaderStrategy()).loadImage(context, FrescoImageConfig.builder().
                    simpleDraweeView(img).url(mDatas.get(position).getUserPicUrl()).build());
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
