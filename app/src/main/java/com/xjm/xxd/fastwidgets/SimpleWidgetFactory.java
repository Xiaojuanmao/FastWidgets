package com.xjm.xxd.fastwidgets;

import android.text.TextUtils;
import android.util.Log;

import com.xjm.xxd.fastwidget.widget.BaseWidget;
import com.xjm.xxd.fastwidget.widget.IWidgetFactory;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * User : retro41
 * Email : wonderfulifeel@gmail.com
 * Date : 17-2-22
 */

public class SimpleWidgetFactory implements IWidgetFactory {

    private static final String TAG = SimpleWidgetFactory.class.getSimpleName();
    @Override
    public List<WidgetConfig> getAllWidgetConfigs() {
        List<WidgetConfig> configs = new ArrayList<>();
        configs.add(new WidgetConfig(NewsWidget.WIDGET_NAME, NewsWidget.WIDGET_ICON_ID, NewsWidget.class.getCanonicalName()));
        configs.add(new WidgetConfig(WeatherWidget.WIDGET_NAME, WeatherWidget.WIDGET_ICON_ID, WeatherWidget.class.getCanonicalName()));
        configs.add(new WidgetConfig(TimeWidget.WIDGET_NAME, TimeWidget.WIDGET_ICON_ID, TimeWidget.class.getCanonicalName()));
        return configs;
    }

    @Override
    public BaseWidget generateWidget(WidgetConfig config) {

        String widgetClassName = config.getWidgetClassName();
        if (!TextUtils.isEmpty(widgetClassName)) {
            try {
                Class clazz = Class.forName(widgetClassName);
                Object widgetObj = clazz.newInstance();
                if (widgetObj instanceof BaseWidget) {
                    Log.e(TAG, "generateWidget(), find widgetObj : " + widgetClassName + " use reflect");
                    return ((BaseWidget) widgetObj);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
