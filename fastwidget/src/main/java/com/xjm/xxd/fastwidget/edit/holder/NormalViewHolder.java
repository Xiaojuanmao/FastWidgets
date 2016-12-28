package com.xjm.xxd.fastwidget.edit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xjm.xxd.fastwidget.R;
import com.xjm.xxd.fastwidget.edit.adapter.EditWidgetItemCallback;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;


/**
 * Created by queda on 2016/12/5.
 */

public class NormalViewHolder extends RecyclerView.ViewHolder {

    private ImageView mAction;
    private ImageView mIcon;
    private TextView mName;
    private ImageView mDrag;

    private boolean mIsAdded = false; // 用来标记当前的holder是否是add的状态
    private WidgetConfig mWidgetConfig;

    private EditWidgetItemCallback mCallback;

    public NormalViewHolder(View itemView, EditWidgetItemCallback callback) {
        super(itemView);
        mAction = (ImageView) itemView.findViewById(R.id.edit_widget_normal_action);
        mIcon = (ImageView) itemView.findViewById(R.id.edit_widget_normal_icon);
        mName = (TextView) itemView.findViewById(R.id.edit_widget_normal_name);
        mDrag = (ImageView) itemView.findViewById(R.id.edit_widget_normal_drag);
        mAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    if (mIsAdded) {
                        mCallback.onRemoveClicked(mWidgetConfig);
                    } else {
                        mCallback.onAddClicked(mWidgetConfig);
                    }
                }
            }
        });
        mCallback = callback;
    }

    public void bindViewHolder(boolean isAdded, WidgetConfig widgetConfig) {
        mIsAdded = isAdded;
        mWidgetConfig = widgetConfig;
        if (mIsAdded) {
            mAction.setImageResource(R.drawable.ic_edit_widget_remove);
            mDrag.setVisibility(View.VISIBLE);
        } else {
            mAction.setImageResource(R.drawable.ic_edit_widget_add);
            mDrag.setVisibility(View.GONE);
        }
        if (mWidgetConfig != null) {
            mIcon.setImageResource(mWidgetConfig.getWidgetIconId());
            mName.setText(mWidgetConfig.getWidgetName());
        }
    }

    public boolean isAdded() {
        return mIsAdded;
    }

}
