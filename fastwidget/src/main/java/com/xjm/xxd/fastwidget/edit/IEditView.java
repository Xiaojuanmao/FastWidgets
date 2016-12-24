package com.xjm.xxd.fastwidget.edit;

import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.util.List;

/**
 * Created by queda on 2016/12/5.
 */

public interface IEditView {

    // 加载已经显示的widget信息成功
    void loadWidgetConfigSuccess(List<WidgetConfig> shownConfigs);

    // 加载已经显示的widget信息失败
    void loadWidgetConfigFailed();
}
