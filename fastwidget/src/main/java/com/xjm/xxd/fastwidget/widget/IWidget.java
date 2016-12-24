package com.xjm.xxd.fastwidget.widget;

/**
 * Created by queda on 2016/12/2.
 */

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * widget必備的特徵
 */
public interface IWidget {

    View onCreate(WeakReference<Context> context);

    void onResume();

    void onDestroy();

    WidgetConfig getConfig();

}
