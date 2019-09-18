package com.nan.nanlib.view.recycleradapter;

import android.content.Context;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public abstract class BaseRecyclerAdapter<T> extends BasePullComplexRecyclerAdapter<T> {

    public BaseRecyclerAdapter(RecyclerView recyclerView, Context context, List<T> data) {
        super(recyclerView, context, data);
    }

    @Override
    public void setOnMultiItemClickListener(OnMultiItemClickListener<T> listener) {
        super.setOnMultiItemClickListener(listener);
        //delegate item click
        SparseArrayCompat<AdapterDelegate<T>> delegates = delegatesManager.getDelegates();
        int size = delegates.size();
        for (int i = 0; i < size; i++) {
            BaseItemDelegate<T> delegate = (BaseItemDelegate<T>) delegates.valueAt(i);
            delegate.mOnMultiItemClickListener = listener;
        }
    }

}
