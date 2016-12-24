package com.xjm.xxd.fastwidget.container;

/**
 * Created by queda on 2016/12/5.
 */

import com.xjm.xxd.fastwidget.widget.WidgetConfig;

/**
 * EditWidgetView 和 GroupContainer之间的回调
 * 用来响应在EditWidgetView中编辑各种widget之后
 * GroupContainer里面进行界面变动
 */

public interface IContainerEditor {

    // 添加了一个widget
    void onWidgetAdded(WidgetConfig widgetConfig);

    // 删除了一个widget
    void onWidgetRemoved(WidgetConfig widgetConfig);

    // 交换了两个widget的位置
    void onWidgetSwap(int firstPos, int secondPos);

}
