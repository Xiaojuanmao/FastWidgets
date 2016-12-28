package com.xjm.xxd.fastwidget.edit;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xjm.xxd.fastwidget.R;
import com.xjm.xxd.fastwidget.container.IContainerEditor;
import com.xjm.xxd.fastwidget.edit.adapter.EditItemTouchHelperCallback;
import com.xjm.xxd.fastwidget.edit.adapter.EditWidgetBaseAdapter;
import com.xjm.xxd.fastwidget.edit.adapter.OnRecyclerItemClickListener;
import com.xjm.xxd.fastwidget.edit.holder.NormalViewHolder;
import com.xjm.xxd.fastwidget.widget.WidgetConfig;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by queda on 2016/12/2.
 */

public class EditWidgetView extends RelativeLayout implements IEditView {

    private ImageView mEnsure;
    private RecyclerView mRecyclerView;

    private boolean mIsAnimating = false;

    private IEditManager mManager;

    private EditWidgetBaseAdapter mAdapter;
    private EditItemTouchHelperCallback mTouchCallback;
    private ItemTouchHelper mItemTouchHelper;

    public EditWidgetView(Context context) {
        this(context, null);
    }

    public EditWidgetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditWidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    private void initViews(Context context) {
        View view = inflate(context, R.layout.layout_edit_widget_view, this);
        mEnsure = (ImageView) view.findViewById(R.id.tool_bar_ensure);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mManager = new EditWidgetManager(new WeakReference<>(getContext()));
        mManager.bindView(this);

        mEnsure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    @Override
    public void loadWidgetConfigSuccess(List<WidgetConfig> shownConfigs) {
        // 拿到了widget列表
        mAdapter.bindShownWidgetConfigs(shownConfigs);
    }

    @Override
    public void loadWidgetConfigFailed() {

    }

    /**
     * 设置adapter
     * @param adapter
     */
    public void setAdapter(EditWidgetBaseAdapter adapter) {
        if (adapter != null) {
            mAdapter = adapter;
            mAdapter.setEditWidgetItemCallback(mManager);
            mRecyclerView.setAdapter(mAdapter);
            mTouchCallback = new EditItemTouchHelperCallback(mAdapter, mAdapter);
            mItemTouchHelper = new ItemTouchHelper(mTouchCallback);
            mItemTouchHelper.attachToRecyclerView(mRecyclerView);
            mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
                @Override
                public void onItemClick(RecyclerView.ViewHolder vh) {

                }

                @Override
                public void onItemLongClick(RecyclerView.ViewHolder vh) {
                    if (vh instanceof NormalViewHolder) {
                        NormalViewHolder viewHolder = ((NormalViewHolder) vh);
                        if (viewHolder.isAdded()) {
                            mItemTouchHelper.startDrag(vh);
                        }
                    }
                }
            });
        }
    }

    public void bindContainerEditor(IContainerEditor editor) {
        if (editor == null || mManager == null) {
            throw new IllegalStateException("The editor or manager can not be null");
        }
        mManager.setContainerEditor(editor);
    }

    /**
     * 对外公开的展示方法
     * 执行出场动画，异步获取数据并刷新界面
     */
    public void show() {
        if (getVisibility() == GONE) {
            if (!mIsAnimating) {
                mIsAnimating = true;
                setVisibility(View.VISIBLE);
                mManager.loadWidgetConfig();
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int height = wm.getDefaultDisplay().getHeight();
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, height, 0);
                translateAnimation.setDuration(300);
                translateAnimation.setInterpolator(new DecelerateInterpolator());
                translateAnimation.setFillAfter(true);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mIsAnimating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startAnimation(translateAnimation);
            }
        }
    }

    /**
     * 对外公开的隐藏方法
     * 执行退场动画
     */
    public void hide() {
        if (getVisibility() == VISIBLE) {
            if (!mIsAnimating) {
                mIsAnimating = true;
                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int height = wm.getDefaultDisplay().getHeight();
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, height);
                translateAnimation.setDuration(300);
                translateAnimation.setInterpolator(new AccelerateInterpolator());
                translateAnimation.setFillAfter(true);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setVisibility(GONE);
                        mIsAnimating = false;
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startAnimation(translateAnimation);
                if (mManager != null) {
                    mManager.onDestroy();
                }
            }
        }
    }

}
