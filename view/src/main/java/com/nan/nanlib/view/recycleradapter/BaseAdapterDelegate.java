package com.nan.nanlib.view.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public abstract class BaseAdapterDelegate<T> extends AbsAdapterDelegate<T> {
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public BaseAdapterDelegate(Context context, int viewType) {
        super(viewType);
        mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent) {
        return new BaseRecyclerViewHolder(mLayoutInflater.inflate(getItemViewLayout(), parent, false));
    }

    public abstract int getItemViewLayout();
}
