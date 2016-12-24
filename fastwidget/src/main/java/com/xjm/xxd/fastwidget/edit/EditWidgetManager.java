package com.xjm.xxd.fastwidget.edit;

import android.content.Context;
import android.util.Log;

import com.xjm.xxd.fastwidget.container.GsonWidgetGroupConfig;
import com.xjm.xxd.fastwidget.container.IContainerEditor;
import com.xjm.xxd.fastwidget.container.IGroupConfig;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by queda on 2016/12/5.
 */

public class EditWidgetManager implements IEditManager {

    private IEditView mView;

    private IGroupConfig mGroupConfig;
    private IContainerEditor mEditor; // container交互的接口，用来控制外面widget控件的变动情况

    private List<WidgetConfig> mShownWidgetConfigs = new LinkedList<>();// 已经在展示的小工具集合

    private static final String TAG = EditWidgetManager.class.getSimpleName();

    public EditWidgetManager(WeakReference<Context> contextWeakReference) {
        mGroupConfig = new GsonWidgetGroupConfig(contextWeakReference);
    }

    @Override
    public void bindView(IEditView view) {
        mView = view;
    }

    @Override
    public void loadWidgetConfig() {
        Observable.create(new Observable.OnSubscribe<List<WidgetConfig>>() {
            @Override
            public void call(Subscriber<? super List<WidgetConfig>> subscriber) {
                subscriber.onNext(mGroupConfig.getConfigs(true));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<WidgetConfig>>() {
                    @Override
                    public void call(List<WidgetConfig> widgetConfigs) {
                        mShownWidgetConfigs.clear();
                        mShownWidgetConfigs.addAll(widgetConfigs);
                        mView.loadWidgetConfigSuccess(mShownWidgetConfigs);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "loadWidgetConfig()", throwable);
                        mView.loadWidgetConfigFailed();
                    }
                });
    }

    @Override
    public void setContainerEditor(IContainerEditor editor) {
        mEditor = editor;
    }

    @Override
    public void onAddClicked(WidgetConfig config) {
        mGroupConfig.addConfig(config);
        mGroupConfig.save();

        mShownWidgetConfigs.add(config);

        if (mEditor != null) {
            mEditor.onWidgetAdded(config);
        }
    }

    @Override
    public void onRemoveClicked(WidgetConfig config) {
        mGroupConfig.removeConfig(config);
        mGroupConfig.save();

        mShownWidgetConfigs.remove(config);

        if (mEditor != null) {
            mEditor.onWidgetRemoved(config);
        }
    }

    @Override
    public void onSwapItem(int firstPos, int secondPos) {
        mGroupConfig.swapConfig(firstPos, secondPos);
        mGroupConfig.save();

        if (mEditor != null) {
            mEditor.onWidgetSwap(firstPos, secondPos);
        }
    }

    @Override
    public void onDestroy() {

    }

}
