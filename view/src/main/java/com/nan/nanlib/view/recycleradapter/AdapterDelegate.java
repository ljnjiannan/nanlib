package com.nan.nanlib.view.recycleradapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public interface AdapterDelegate<T> {
    /**
     * Get the view type integer. Must be unique within every Adapter
     *
     * @return the integer representing the view type
     */
    public int getItemViewType();

    /**
     * Called to determine whether this AdapterDelegate is the responsible for the given mData
     * element.
     *
     * @param item The mData source of the Adapter
     * @param position The position in the datasource
     * @return true, if this item is responsible,  otherwise false
     */
    public boolean isForViewType(@NonNull T item, int position);

    /**
     * Creates the  {@link RecyclerView.ViewHolder} for the given mData source item
     *
     * @param parent The ViewGroup parent of the given datasource
     * @return The new instantiated {@link RecyclerView.ViewHolder}
     */
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent);

    /**
     * Called to bind the {@link RecyclerView.ViewHolder} to the item of the datas source set
     *
     * @param item The mData source
     * @param position The position in the datasource
     * @param holder The {@link RecyclerView.ViewHolder} to bind
     */
    public void onBindViewHolder(@NonNull T item, int position,
                                 @NonNull BaseRecyclerViewHolder holder);
}
