package com.xjm.xxd.fastwidget.edit.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xjm.xxd.fastwidget.R;
import com.xjm.xxd.fastwidget.edit.adapter.EditWidgetItemCallback;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by queda on 2016/12/5.
 */

public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.edit_widget_normal_action)
    ImageView mAction;
    @BindView(R.id.edit_widget_normal_icon)
    ImageView mIcon;
    @BindView(R.id.edit_widget_normal_name)
    TextView mName;
    @BindView(R.id.edit_widget_normal_drag)
    ImageView mDrag;

    private boolean mIsAdded = false; // 用来标记当前的holder是否是add的状态
    private WidgetConfig mWidgetConfig;

    private EditWidgetItemCallback mCallback;

    public NormalViewHolder(View itemView, EditWidgetItemCallback callback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mAction.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_widget_normal_action:
                if (mCallback != null) {
                    if (mIsAdded) {
                        mCallback.onRemoveClicked(mWidgetConfig);
                    } else {
                        mCallback.onAddClicked(mWidgetConfig);
                    }
                }
                break;
        }
    }

    public boolean isAdded() {
        return mIsAdded;
    }

}
