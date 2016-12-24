package com.xjm.xxd.fastwidget.container;

import android.view.View;

/**
 * Created by queda on 2016/12/2.
 */

interface IGroupContainer {

    // 添加一個view
    void addWidgetView(View view);

    // 移除view
    void removeWidgetView(View view);

    // 交換兩個view的位置
    void swapWidgetView(View firstView, View secondView);

}
