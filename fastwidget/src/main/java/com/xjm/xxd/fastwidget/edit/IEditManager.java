package com.xjm.xxd.fastwidget.edit;

import com.xjm.xxd.fastwidget.container.IContainerEditor;
import com.xjm.xxd.fastwidget.edit.adapter.EditWidgetItemCallback;

/**
 * Created by queda on 2016/12/5.
 */

public interface IEditManager extends EditWidgetItemCallback {

    void bindView(IEditView view);

    void loadWidgetConfig();

    // 绑定和container交互的接口
    void setContainerEditor(IContainerEditor editor);

    void onDestroy();

}
