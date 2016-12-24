package com.xjm.xxd.fastwidget.container;

import com.xjm.xxd.fastwidget.widget.BaseWidget;

/**
 * Created by queda on 2016/12/2.
 */

interface IGroupManager extends IContainerEditor {

    void bindContainer(IGroupContainer groupContainer);

    void addWidget(BaseWidget widget, boolean isNeedAddToConfig);

    void removeWidget(BaseWidget widget);

    void saveWidgetConfig();

    void destroy();
}
