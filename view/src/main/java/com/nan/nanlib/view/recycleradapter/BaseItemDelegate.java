package com.nan.nanlib.view.recycleradapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public abstract class BaseItemDelegate<T> extends BaseAdapterDelegate<T> {

    private static final String TAG = "BaseItemDelegate";
    /**
     * item点击事件
     */
    protected BaseSimpleRecyclerAdapter.OnMultiItemClickListener<T> mOnMultiItemClickListener = null;

    public BaseItemDelegate(Context context, int viewType) {
        super(context, viewType);
    }

    public void setOnMultiItemClickListener(BaseSimpleRecyclerAdapter.OnMultiItemClickListener<T> listener) {
        this.mOnMultiItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull T item, int position, @NonNull BaseRecyclerViewHolder holder) {
        setClickListener(holder.itemView, item, position);
    }

    protected void setClickListener(final View itemView, final T item, final int position) {
        if (mOnMultiItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnMultiItemClickListener.onMultiItemClick(itemView, item, position, viewType);
                }
            });
        }
    }

}
