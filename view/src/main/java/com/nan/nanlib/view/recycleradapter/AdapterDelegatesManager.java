package com.nan.nanlib.view.recycleradapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public class AdapterDelegatesManager<T> {


    /**
     * Map for ViewType to AdapterDeleage
     */
    SparseArrayCompat<AdapterDelegate<T>> delegates = new SparseArrayCompat();

    /**
     * Adds an {@link AdapterDelegate}. Internally calls {@link #addDelegate(AdapterDelegate,
     * boolean)} with false as parameter.
     *
     * @param delegate the delegate to addAll
     * @return self
     * @throws IllegalArgumentException if an {@link AdapterDelegate} is already added (registered)
     * with the same ViewType {@link AdapterDelegate#getItemViewType()}.
     * @see #addDelegate(AdapterDelegate, boolean)
     */
    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate) {
        return addDelegate(delegate, false);
    }

    /**
     * Adds an {@link AdapterDelegate}.
     *
     * @param delegate The delegate to addAll
     * @param allowReplacingDelegate if true, you allow to replacing the given delegate any previous
     * delegate for the same view type. if false, you disallow and a {@link IllegalArgumentException}
     * will be thrown if you try to replace an already registered {@link AdapterDelegate} for the
     * same
     * view type.
     * @throws IllegalArgumentException if <b>allowReplacingDelegate</b>  is false and an {@link
     * AdapterDelegate} is already added (registered)
     * with the same ViewType {@link AdapterDelegate#getItemViewType()}.
     */
    public AdapterDelegatesManager<T> addDelegate(@NonNull AdapterDelegate<T> delegate,
                                                  boolean allowReplacingDelegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null!");
        }

        int viewType = delegate.getItemViewType();
        if (!allowReplacingDelegate && delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An AdapterDelegate is already registered for the viewType = " + viewType
                            + ". Already registered AdapterDelegate is " + delegates.get(viewType));
        }

        delegates.put(viewType, delegate);

        return this;
    }

    /**
     * Removes a previously registered delegate if and only if the passed delegate is registered
     * (checks the reference of the object). This will not remove any other delegate for the same
     * viewType (if there is any).
     *
     * @param delegate The delegate to remove
     * @return self
     */
    public AdapterDelegatesManager<T> removeDelegate(@NonNull AdapterDelegate<T> delegate) {

        if (delegate == null) {
            throw new NullPointerException("AdapterDelegate is null");
        }

        AdapterDelegate<T> queried = delegates.get(delegate.getItemViewType());
        if (queried != null && queried == delegate) {
            delegates.remove(delegate.getItemViewType());
        }
        return this;
    }

    /**
     * Removes the adapterDelegate for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public AdapterDelegatesManager<T> removeDelegate(int viewType) {
        delegates.remove(viewType);
        return this;
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#getItemViewType(int)}. Internally it scans all
     * the registered {@link AdapterDelegate} and picks the right one to return the ViewType integer.
     *
     * @param items Adapter's mData source
     * @param position the position in adapters mData source
     * @return the ViewType (integer)
     * @throws IllegalArgumentException if no {@link AdapterDelegate} has been found that is
     * responsible for the given mData element in mData set (No {@link AdapterDelegate} for the given
     * ViewType)
     * @throws NullPointerException if items is null
     */
    public int getItemViewType(@NonNull T items, int position) {

        if (items == null) {
            throw new NullPointerException("Items datasource is null!");
        }

        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            AdapterDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(items, position)) {
                return delegate.getItemViewType();
            }
        }

        throw new IllegalArgumentException(
                "No AdapterDelegate added that matches position=" + position + " in mData source");
    }

    /**
     * This method must be called in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     *
     * @param parent the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     * viewType
     */
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterDelegate<T> delegate = delegates.get(viewType);
        if (delegate == null) {
            throw new NullPointerException("No AdapterDelegate added for ViewType " + viewType);
        }

        BaseRecyclerViewHolder vh = delegate.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException(
                    "ViewHolder returned from AdapterDelegate " + delegate + " for ViewType =" + viewType
                            + " is null!");
        }
        return vh;
    }

    /**
     * Must be called from{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param items Adapter's mData source
     * @param position the position in mData source
     * @param viewHolder the ViewHolder to bind
     * @throws NullPointerException if no AdapterDelegate has been registered for ViewHolders
     * viewType
     */
    public void onBindViewHolder(@NonNull T items, int position,
                                 @NonNull BaseRecyclerViewHolder viewHolder) {

        AdapterDelegate<T> delegate = delegates.get(viewHolder.getItemViewType());
        if (delegate == null) {
            throw new NullPointerException(
                    "No AdapterDelegate added for ViewType " + viewHolder.getItemViewType());
        }
        delegate.onBindViewHolder(items, position, viewHolder);
    }

    public SparseArrayCompat<AdapterDelegate<T>> getDelegates() {
        return delegates;
    }
}
