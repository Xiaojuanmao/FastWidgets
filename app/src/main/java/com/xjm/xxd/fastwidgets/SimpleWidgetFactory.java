package com.xjm.xxd.fastwidgets;

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
        BaseWidget baseWidget = null;
        switch (config.getWidgetName()) {
            case NewsWidget.WIDGET_NAME:
                baseWidget = new NewsWidget();
                break;

            case WeatherWidget.WIDGET_NAME:
                baseWidget = new WeatherWidget();
                break;

            case TimeWidget.WIDGET_NAME:
                baseWidget = new TimeWidget();
                break;
        }
        return baseWidget;
    }

}
