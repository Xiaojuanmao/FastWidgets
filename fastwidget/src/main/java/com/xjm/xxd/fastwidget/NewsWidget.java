package com.xjm.xxd.fastwidget;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xjm.xxd.fastwidget.widget.BaseWidget;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * Created by queda on 2016/12/5.
 */

public class NewsWidget extends BaseWidget {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final String WIDGET_NAME = "新闻插件";
    public static final int WIDGET_ICON_ID = R.drawable.ic_widget_news;

    @Override
    protected View createView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.layout_news_widget, null);
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
                mTitle.setText("News Title");
                mTitle.setVisibility(View.VISIBLE);
                mImage.setVisibility(View.VISIBLE);
                mImage.setImageResource(R.drawable.img_news_widget);
            }
        }, 2000);
    }



    @Override
    protected void perfectConfigInfo(@NonNull WidgetConfig config) {
        config.setWidgetName(WIDGET_NAME);
        config.setWidgetIconId(WIDGET_ICON_ID);
    }

}
