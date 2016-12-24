package com.xjm.xxd.fastwidget;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextClock;

import com.xjm.xxd.fastwidget.widget.BaseWidget;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by queda on 2016/12/2.
 */

public class TimeWidget extends BaseWidget {

    @BindView(R.id.text_clock)
    TextClock mTextClock;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final String WIDGET_NAME = "时钟插件";
    public static final int WIDGET_ICON_ID = R.drawable.ic_widget_time;

    @Override
    protected View createView(LayoutInflater layoutInflater) {
        if (layoutInflater == null) {
            return null;
        }
        View view = layoutInflater.inflate(R.layout.layout_time_widget, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mProgressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(GONE);
                mTextClock.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    @Override
    protected void perfectConfigInfo(@NonNull WidgetConfig config) {
        config.setWidgetName(WIDGET_NAME);
        config.setWidgetIconId(WIDGET_ICON_ID);
    }

}
