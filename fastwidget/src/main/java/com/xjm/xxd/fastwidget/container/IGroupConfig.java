package com.xjm.xxd.fastwidget.container;

import android.support.annotation.NonNull;

import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.util.List;

/**
 * Created by queda on 2016/12/2.
 */

public interface IGroupConfig {

    // 向配置文件中添加一个widget信息
    void addConfig(WidgetConfig config);

    // 从配置文件中删除一个widget信息
    boolean removeConfig(WidgetConfig config);

    // 交换两个配置信息的位置
    void swapConfig(int firstPos, int secondPos);

    // 从配置文件中取出添加过的widget信息
    @NonNull
    List<WidgetConfig> getConfigs(boolean isNeedReload);

    // 将当前配置保存起来
    void save();

}
