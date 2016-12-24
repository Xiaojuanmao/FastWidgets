package com.xjm.xxd.fastwidget.edit.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xjm.xxd.fastwidget.R;
import com.xjm.xxd.fastwidget.edit.holder.GroupViewHolder;
import com.xjm.xxd.fastwidget.edit.holder.HeaderViewHolder;
import com.xjm.xxd.fastwidget.edit.holder.NormalViewHolder;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by queda on 2016/12/5.
 */

public abstract class EditWidgetBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements EditWidgetItemCallback {

    private static final int ITEM_TYPE_HEADER = 0; // 头部
    private static final int ITEM_TYPE_ADDED_TITLE = 1; // 已经被添加的组件头部
    private static final int ITEM_TYPE_ADDED = 2; // 已经被添加的组件
    private static final int ITEM_TYPE_NOT_ADD_TITLE = 3; // 没有被添加的组件头部
    private static final int ITEM_TYPE_NOT_ADD = 4; // 没有被添加的组件

    private LayoutInflater mInflater;
    private EditWidgetItemCallback mItemCallback; // 用来反馈item点击事件

    private List<WidgetConfig> mShownWidgetConfig = new LinkedList<>(); // 已经处于展示状态的列表
    private List<WidgetConfig> mNotShownWidgetConfig = new LinkedList<>(); // 处于关闭状态的列表
    protected List<WidgetConfig> mAllWidgetConfig = new LinkedList<>(); // 全部组件的列表

    private static final String TAG = EditWidgetBaseAdapter.class.getSimpleName();

    private EditWidgetBaseAdapter() {

    }

    public EditWidgetBaseAdapter(LayoutInflater inflater) {
        mInflater = inflater;
        initAllWidgetConfigs();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // 第一個為header
            return ITEM_TYPE_HEADER;
        }
        if (position == 1) {
            return ITEM_TYPE_ADDED_TITLE;
        }
        if (position < 1 + getShownItemCount()) {
            return ITEM_TYPE_ADDED;
        }
        if (position == 1 + getShownItemCount()) {
            return ITEM_TYPE_NOT_ADD_TITLE;
        }
        return ITEM_TYPE_NOT_ADD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder result = null;
        switch (viewType) {
            case ITEM_TYPE_HEADER:
                result = new HeaderViewHolder(mInflater.inflate(R.layout.item_edit_widget_header, parent, false));
                break;

            case ITEM_TYPE_ADDED_TITLE:
                result = new GroupViewHolder(mInflater.inflate(R.layout.item_edit_widget_group_header, parent, false));
                break;

            case ITEM_TYPE_ADDED:
                result = new NormalViewHolder(mInflater.inflate(R.layout.item_edit_widget_normal, parent, false), this);
                break;

            case ITEM_TYPE_NOT_ADD_TITLE:
                result = new GroupViewHolder(mInflater.inflate(R.layout.item_edit_widget_group_header, parent, false));
                break;

            case ITEM_TYPE_NOT_ADD:
                result = new NormalViewHolder(mInflater.inflate(R.layout.item_edit_widget_normal, parent, false), this);
                break;
        }
        if (result == null) {
            result = new NormalViewHolder(mInflater.inflate(R.layout.item_edit_widget_normal, parent, false), this);
        }
        return result;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case ITEM_TYPE_HEADER:

                break;

            case ITEM_TYPE_ADDED_TITLE:
                GroupViewHolder addedHolder = ((GroupViewHolder) holder);
                addedHolder.mGroupTitle.setText(R.string.add_widget);
                break;

            case ITEM_TYPE_ADDED:
                WidgetConfig addedWidgetConfig = mShownWidgetConfig.get(position - 2);
                NormalViewHolder addedNormalHolder = ((NormalViewHolder) holder);
                addedNormalHolder.bindViewHolder(true, addedWidgetConfig);
                break;

            case ITEM_TYPE_NOT_ADD_TITLE:
                GroupViewHolder notAddHolder = ((GroupViewHolder) holder);
                notAddHolder.mGroupTitle.setText(R.string.more_widget);
                break;

            case ITEM_TYPE_NOT_ADD:
                WidgetConfig notAddedWidgetConfig = mNotShownWidgetConfig.get(position - 2 - getShownItemCount());
                NormalViewHolder notAddNormalHolder = ((NormalViewHolder) holder);
                notAddNormalHolder.bindViewHolder(false, notAddedWidgetConfig);
                break;
        }
    }

    /**
     * 最頂部一個header加上兩個模塊的item個數
     */
    @Override
    public int getItemCount() {
        return 1 + getShownItemCount() + getNotShownItemCount();
    }

    /**
     * 獲取到處於激活狀態的widget以及header的個數
     *
     * @return
     */
    private int getShownItemCount() {
        if (mShownWidgetConfig == null || mShownWidgetConfig.isEmpty()) {
            return 1;
        }
        return 1 + mShownWidgetConfig.size();
    }

    /**
     * 獲取到處於未激活狀態的widget以及header的個數
     *
     * @return
     */
    private int getNotShownItemCount() {
        if (mNotShownWidgetConfig == null || mNotShownWidgetConfig.isEmpty()) {
            return 1;
        }
        return 1 + mNotShownWidgetConfig.size();
    }

    /**
     * 通过config来查找位置
     * 执行动画需要
     *
     * @param config
     * @return
     */
    private int getWidgetConfigPosition(WidgetConfig config) {
        if (config == null) {
            return -1;
        }
        if (-1 != mShownWidgetConfig.indexOf(config)) {
            return 2 + mShownWidgetConfig.indexOf(config);
        }
        if (-1 != mNotShownWidgetConfig.indexOf(config)) {
            return 2 + getShownItemCount() + mNotShownWidgetConfig.indexOf(config);
        }
        return -1;
    }

    @Override
    public void onAddClicked(WidgetConfig config) {

        int fromPos = getWidgetConfigPosition(config);
        if (fromPos == -1) {
            return;
        }
        int toPos = 1 + getShownItemCount();
        boolean removeResult = mNotShownWidgetConfig.remove(config);
        if (removeResult) {
            mShownWidgetConfig.add(config);
            notifyItemMoved(fromPos, toPos);
            if (mItemCallback != null) {
                mItemCallback.onAddClicked(config);
            }
        }
    }

    @Override
    public void onRemoveClicked(WidgetConfig config) {

        int fromPos = getWidgetConfigPosition(config);
        if (fromPos == -1) {
            return;
        }
        int toPos = 1 + getShownItemCount();
        boolean removeResult = mShownWidgetConfig.remove(config);
        if (removeResult) {
            mNotShownWidgetConfig.add(0, config);
            notifyItemMoved(fromPos, toPos);
            if (mItemCallback != null) {
                mItemCallback.onRemoveClicked(config);
            }
        }
    }

    @Override
    public void onSwapItem(int firstPos, int secondPos) {

        int firstType = getItemViewType(firstPos);
        int secondType = getItemViewType(secondPos);

        if (firstType == ITEM_TYPE_ADDED && secondType == ITEM_TYPE_ADDED) {
            Collections.swap(mShownWidgetConfig, firstPos - 2, secondPos - 2);
            if (mItemCallback != null) {
                mItemCallback.onSwapItem(firstPos - 2, secondPos - 2);
            }
        }
    }

    public void setEditWidgetItemCallback(EditWidgetItemCallback callback) {
        mItemCallback = callback;
    }

    public void bindShownWidgetConfigs(List<WidgetConfig> shownConfigs) {

        // TODO : 存在等待的情況，可能需要用進度條提示用戶

        Observable.just(shownConfigs)
                .doOnNext(new Action1<List<WidgetConfig>>() {
                    @Override
                    public void call(List<WidgetConfig> widgetConfigs) {
                        // 重置當前已經顯示的列表
                        mShownWidgetConfig.clear();
                        mShownWidgetConfig.addAll(widgetConfigs);

                        mNotShownWidgetConfig.clear();
                        // 篩選出處於隱藏狀態的列表進行
                        for (WidgetConfig config : mAllWidgetConfig) {
                            if (!mShownWidgetConfig.contains(config)) {
                                mNotShownWidgetConfig.add(config);
                            }
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<WidgetConfig>>() {
                    @Override
                    public void call(List<WidgetConfig> widgetConfigs) {
                        // 數據源更新完成，刷新界面
                        notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "bindShownWidgetConfigs()", throwable);
                    }
                });
    }

    /**
     * 用来初始化全量的widget信息列表
     */
    protected abstract void initAllWidgetConfigs();

}
