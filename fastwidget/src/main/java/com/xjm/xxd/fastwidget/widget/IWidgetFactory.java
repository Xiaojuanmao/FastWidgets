package com.xjm.xxd.fastwidget.widget;

import java.util.List;

/**
 * User : retro41
 * Email : wonderfulifeel@gmail.com
 * Date : 17-2-22
 */

public interface IWidgetFactory {

    List<WidgetConfig> getAllWidgetConfigs();

    BaseWidget generateWidget(WidgetConfig config);

}
