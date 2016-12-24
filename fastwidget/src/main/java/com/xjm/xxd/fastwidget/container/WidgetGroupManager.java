package com.xjm.xxd.fastwidget.container;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.xjm.xxd.fastwidget.widget.BaseWidget;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by queda on 2016/12/2.
 */

public class WidgetGroupManager implements IGroupManager {

    private IGroupContainer mContainer;

    private IGroupConfig mConfig;

    private List<BaseWidget> mLinkedWidgets; // 存放着当前widget的顺序
    private Map<BaseWidget, View> mWidgetViewMap; // 管理widget和view对应的关系
    private WeakReference<Context> mContextReference;

    private static final String TAG = WidgetGroupManager.class.getSimpleName();

    public WidgetGroupManager(Context context) {
        mContextReference = new WeakReference<>(context);
        mWidgetViewMap = new HashMap<>();
        mLinkedWidgets = new LinkedList<>();
        mConfig = new GsonWidgetGroupConfig(mContextReference);
    }

    /**
     * 读取配置文件
     * 逐个生成widget并显示
     */
    private void initWithConfig() {
        List<WidgetConfig> configList = mConfig.getConfigs(false);
        if (!configList.isEmpty()) {
            for (WidgetConfig config : configList) {
                BaseWidget baseWidget = generateBaseWidget(config);
                if (baseWidget != null) {
                    addWidget(baseWidget, false);
                }
            }
        }
    }

    /**
     * 通过反射来根据配置生成Widget
     * @param config
     * @return
     */
    private BaseWidget generateBaseWidget(WidgetConfig config) {
        if (config == null) {
            return null;
        }
        String widgetClassName = config.getWidgetClassName();
        if (!TextUtils.isEmpty(widgetClassName)) {
            try {
                Class clazz = Class.forName(widgetClassName);
                Object widgetObj = clazz.newInstance();
                if (widgetObj instanceof BaseWidget) {
                    Log.e(TAG, "initWithConfig(), find widgetObj : " + widgetClassName + " use reflect");
                    return ((BaseWidget) widgetObj);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void bindContainer(IGroupContainer groupContainer) {
        mContainer = groupContainer;
        initWithConfig();
    }

    @Override
    public void onWidgetAdded(WidgetConfig widgetConfig) {
        // 在EditWidgetView中添加了widget
        if (widgetConfig == null) {
            return;
        }
        BaseWidget baseWidget = generateBaseWidget(widgetConfig);
        if (baseWidget != null) {
            addWidget(baseWidget, true);
        }
    }

    @Override
    public void onWidgetRemoved(WidgetConfig widgetConfig) {
        // 在EditWidgetView中移除了widget
        if (widgetConfig == null) {
            return;
        }
        BaseWidget baseWidget = generateBaseWidget(widgetConfig);
        if (baseWidget != null) {
            removeWidget(baseWidget);
        }
    }

    @Override
    public void onWidgetSwap(int firstPos, int secondPos) {
        if (firstPos >= 0 && firstPos < mLinkedWidgets.size()
                && secondPos >= 0 && secondPos < mLinkedWidgets.size()) {

            BaseWidget firstWidget = mLinkedWidgets.get(firstPos);
            BaseWidget secondWidget = mLinkedWidgets.get(secondPos);
            if (firstWidget != null && secondWidget != null) {

                // 交換數據源的位置
                Collections.swap(mLinkedWidgets, firstPos, secondPos);

                View firstView = mWidgetViewMap.get(firstWidget);
                View secondView = mWidgetViewMap.get(secondWidget);

                if (firstView != null && secondView != null) {
                    // 交換view的位置
                    mContainer.swapWidgetView(firstView, secondView);
                }
            }
        }
    }

    @Override
    public void addWidget(BaseWidget widget, boolean isNeedAddToConfig) {
        if (widget == null) {
            Log.w(TAG, "addWidget(), widget is null");
            return;
        }
        if (mWidgetViewMap == null) {
            mWidgetViewMap = new HashMap<>();
            Log.w(TAG, "addWidget(), widget -> view map is null");
        }
        if (mWidgetViewMap.get(widget) != null) {
            Log.w(TAG, "addWidget(), widget already exist");
            return;
        }
        View view = widget.onCreate(mContextReference);
        if (view == null) {
            Log.w(TAG, "addWidget(), onCreate widget error");
            return;
        }
        mLinkedWidgets.add(widget);
        mWidgetViewMap.put(widget, view);
        mContainer.addWidgetView(view);
        if (isNeedAddToConfig) {
            mConfig.addConfig(widget.getConfig());
        }
        widget.onResume();
    }

    @Override
    public void removeWidget(BaseWidget widget) {
        if (widget == null) {
            Log.w(TAG, "removeWidget(), widget is null");
            return;
        }
        if (mWidgetViewMap == null) {
            mWidgetViewMap = new HashMap<>();
            Log.w(TAG, "removeWidget(), widget -> view map is null");
        }
        if (mWidgetViewMap.get(widget) == null) {
            Log.w(TAG, "removeWidget(), widget not exist");
            return;
        }
        mLinkedWidgets.remove(widget);
        View viewNeedRemove = mWidgetViewMap.remove(widget);
        mContainer.removeWidgetView(viewNeedRemove);
        widget.onDestroy();
    }

    @Override
    public void saveWidgetConfig() {
        if (mConfig != null) {
            mConfig.save();
        }
    }

    @Override
    public void destroy() {
        if (mConfig != null) {
            mConfig.save();
        }
    }

}
