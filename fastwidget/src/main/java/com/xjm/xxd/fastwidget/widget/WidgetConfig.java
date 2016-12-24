package com.xjm.xxd.fastwidget.widget;

/**
 * Created by queda on 2016/12/2.
 */

import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 每一个显示在container上的widget都有一个config
 * config里面保存了widget的相关信息，能够通过config来确定widget
 */

public class WidgetConfig implements Serializable {

    @SerializedName("widget_name")
    private String mWidgetName; // 插件名称，用于管理的时候进行展示
    @SerializedName("widget_icon")
    private
    @DrawableRes
    int mWidgetIconId; // 插件图标id
    @SerializedName("widget_class_name")
    private String mWidgetClassName; // 插件类名，用于新建插件

    public WidgetConfig() {

    }

    public WidgetConfig(String widgetName, @DrawableRes int iconId, String widgetClassName) {
        mWidgetName = widgetName;
        mWidgetIconId = iconId;
        mWidgetClassName = widgetClassName;
    }

    public String getWidgetClassName() {
        return mWidgetClassName;
    }

    public void setWidgetClassName(String widgetClassName) {
        mWidgetClassName = widgetClassName;
    }

    public String getWidgetName() {
        return mWidgetName;
    }

    public void setWidgetName(String widgetName) {
        mWidgetName = widgetName;
    }

    public int getWidgetIconId() {
        return mWidgetIconId;
    }

    public void setWidgetIconId(int widgetIconId) {
        mWidgetIconId = widgetIconId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof WidgetConfig)) {
            return false;
        }
        WidgetConfig target = ((WidgetConfig) o);
        String targetClassName = target.getWidgetClassName();
        if (TextUtils.isEmpty(targetClassName) || (TextUtils.isEmpty(mWidgetClassName))) {
            return false;
        }
        return mWidgetClassName.equals(targetClassName);
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + mWidgetClassName.hashCode();
        return result;
    }
}
