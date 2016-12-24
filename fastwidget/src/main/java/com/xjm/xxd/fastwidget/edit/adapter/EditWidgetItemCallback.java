package com.xjm.xxd.fastwidget.edit.adapter;

import com.xjm.xxd.fastwidget.widget.WidgetConfig;

/**
 * Created by queda on 2016/12/5.
 */

public interface EditWidgetItemCallback {

    void onAddClicked(WidgetConfig config);

    void onRemoveClicked(WidgetConfig config);

    void onSwapItem(int firstPos, int secondPos);
}
