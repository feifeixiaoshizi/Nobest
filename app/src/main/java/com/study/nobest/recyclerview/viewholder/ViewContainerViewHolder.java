package com.study.nobest.recyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.study.nobest.R;

/**
 * Created by jianshengli on 2018/4/30.
 * 头布局的根布局是个FrameLayout负责提供容器。
 */

public class ViewContainerViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout viewContainer;
    public ViewContainerViewHolder(View itemView) {
        super(itemView);
        viewContainer=itemView.findViewById(R.id.header_view_container);

    }





}
