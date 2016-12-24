package com.xjm.xxd.fastwidget;

import android.view.LayoutInflater;

import com.xjm.xxd.fastwidget.edit.adapter.EditWidgetBaseAdapter;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

/**
 * Created by queda on 2016/12/6.
 */

public class EditWidgetAdapter extends EditWidgetBaseAdapter {

    public EditWidgetAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    protected void initAllWidgetConfigs() {
        mAllWidgetConfig.add(new WidgetConfig(WeatherWidget.WIDGET_NAME, WeatherWidget.WIDGET_ICON_ID, WeatherWidget.class.getCanonicalName()));
        mAllWidgetConfig.add(new WidgetConfig(TimeWidget.WIDGET_NAME, TimeWidget.WIDGET_ICON_ID, TimeWidget.class.getCanonicalName()));
        mAllWidgetConfig.add(new WidgetConfig(NewsWidget.WIDGET_NAME, NewsWidget.WIDGET_ICON_ID, NewsWidget.class.getCanonicalName()));
    }

}
