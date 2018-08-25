package com.study.nobest.recyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.study.nobest.R;

/**
 * Created by jianshengli on 2018/4/30.
 */

public class MyViewHolder1 extends RecyclerView.ViewHolder {
    public SimpleDraweeView simpleDraweeView;
    public MyViewHolder1(View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.img);
    }





}
