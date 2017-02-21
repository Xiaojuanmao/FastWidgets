package com.xjm.xxd.fastwidget.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by queda on 2016/12/2.
 */

public abstract class BaseWidget implements IWidget {

    private View mRootView;

    private WeakReference<Context> mContextReference;

    private WidgetConfig mConfig;

    @Override
    public View onCreate(WeakReference<Context> contextWeakReference) {
        mContextReference = contextWeakReference;
        if (getLayoutInflater() == null) {
            return null;
        }
        LayoutInflater layoutInflater = getLayoutInflater();
        mRootView = createView(layoutInflater);
        return mRootView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public WidgetConfig getConfig() {
        if (mConfig == null) {
            mConfig = new WidgetConfig();
            mConfig.setWidgetClassName(this.getClass().getCanonicalName());
        }
        perfectConfigInfo(mConfig);
        return mConfig;
    }

    protected
    @Nullable
    LayoutInflater getLayoutInflater() {
        if (getContext() == null) {
            return null;
        }
        return LayoutInflater.from(getContext());
    }

    protected
    @Nullable
    Context getContext() {
        if (mContextReference != null && mContextReference.get() != null) {
            return mContextReference.get();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof BaseWidget)) {
            return false;
        }
        BaseWidget target = ((BaseWidget) o);
        WidgetConfig targetConfig = target.getConfig();
        if (targetConfig == null || getConfig() == null) {
            return false;
        }
        return targetConfig.equals(getConfig());
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + getConfig().hashCode();
        return result;
    }

    protected abstract View createView(LayoutInflater layoutInflater);

    protected abstract void perfectConfigInfo(@NonNull WidgetConfig config);
}
