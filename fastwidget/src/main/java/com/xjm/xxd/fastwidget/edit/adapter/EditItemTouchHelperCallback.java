package com.xjm.xxd.fastwidget.edit.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xjm.xxd.fastwidget.edit.holder.NormalViewHolder;

/**
 * Created by queda on 2016/12/5.
 */

public class EditItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private RecyclerView.Adapter mAdapter;
    private EditWidgetItemCallback mCallback;

    public EditItemTouchHelperCallback(RecyclerView.Adapter adapter, EditWidgetItemCallback callback) {
        mAdapter = adapter;
        mCallback = callback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position

        if (!(viewHolder instanceof NormalViewHolder) || !(target instanceof NormalViewHolder)) {
            return false;
        }
        NormalViewHolder normalViewHolder = ((NormalViewHolder) viewHolder);
        NormalViewHolder targetViewHolder = ((NormalViewHolder) target);
        if (!(normalViewHolder.isAdded()) || (!(targetViewHolder.isAdded()))) {
            return false;
        }

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                mCallback.onSwapItem(i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                mCallback.onSwapItem(i, i - 1);
            }
        }

        mAdapter.notifyItemMoved(fromPosition, toPosition);

        return true;
    }

    //当长按选中item的时候（拖拽开始的时候）调用
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    //当手指松开的时候（拖拽完成的时候）调用
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(0);
    }

    /**
     * 禁用长按拖拽事件
     * holder里单独实现
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

}
