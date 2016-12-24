package com.xjm.xxd.fastwidget.edit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xjm.xxd.fastwidget.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by queda on 2016/12/5.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.edit_widget_group_title)
    public TextView mGroupTitle;

    public GroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
