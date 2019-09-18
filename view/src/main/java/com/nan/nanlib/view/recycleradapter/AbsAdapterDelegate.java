package com.nan.nanlib.view.recycleradapter;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
abstract public class AbsAdapterDelegate<T> implements AdapterDelegate<T> {

    /**
     * The item view type
     */
    protected int viewType;

    public AbsAdapterDelegate(int viewType) {
        this.viewType = viewType;
    }

    @Override
    public int getItemViewType() {
        return viewType;
    }

}
