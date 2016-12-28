package com.xjm.xxd.fastwidget.edit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xjm.xxd.fastwidget.R;

/**
 * Created by queda on 2016/12/5.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView mGroupTitle;

    public GroupViewHolder(View itemView) {
        super(itemView);
        mGroupTitle = (TextView) itemView.findViewById(R.id.edit_widget_group_title);
    }

}
