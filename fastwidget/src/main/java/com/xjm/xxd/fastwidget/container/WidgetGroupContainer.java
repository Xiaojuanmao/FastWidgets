package com.xjm.xxd.fastwidget.container;

/**
 * Created by queda on 2016/12/2.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xjm.xxd.fastwidget.widget.IWidgetFactory;

/**
 * 作为所有组件的父容器
 * 管理所有组件的生命周期
 */

public class WidgetGroupContainer extends ScrollView
        implements IGroupContainer {

    private IGroupManager mManager;

    private LinearLayout mRootContainer;

    public WidgetGroupContainer(Context context) {
        this(context, null);
    }

    public WidgetGroupContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetGroupContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (mRootContainer == null) {
            mRootContainer = new LinearLayout(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mRootContainer.setLayoutParams(layoutParams);
            mRootContainer.setOrientation(LinearLayout.VERTICAL);
        }
        addView(mRootContainer);
    }

    @Override
    public void addWidgetView(View view) {
        if (view == null) {
            return;
        }
        mRootContainer.addView(view);
    }

    @Override
    public void removeWidgetView(View view) {
        if (view == null) {
            return;
        }
        mRootContainer.removeView(view);
    }

    @Override
    public void swapWidgetView(View firstView, View secondView) {
        if (firstView == null || secondView == null) {
            return;
        }
        int firstIndex = mRootContainer.indexOfChild(firstView);
        int secondIndex = mRootContainer.indexOfChild(secondView);
        mRootContainer.removeView(firstView);
        mRootContainer.removeView(secondView);
        if (firstIndex < secondIndex) {
            mRootContainer.addView(secondView, firstIndex);
            mRootContainer.addView(firstView, secondIndex);
        } else {
            mRootContainer.addView(firstView, secondIndex);
            mRootContainer.addView(secondView, firstIndex);
        }
    }

    public IContainerEditor edit() {
        return mManager;
    }

    public void setWidgetFactory(IWidgetFactory factory) {
        mManager = new WidgetGroupManager(getContext());
        mManager.setWidgetFactory(factory);
        mManager.bindContainer(this);
    }

}
