package com.nan.nanlib.view.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public abstract class BaseSimpleRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected List<T> mDatas;
    /**
     * 在delegate中使用
     */
    protected OnMultiItemClickListener mOnMultiItemClickListener = null;
    protected OnItemClickListener mOnItemClickListener = null;
    protected OnItemLongClickListener mOnItemLongClickListener = null;

    public BaseSimpleRecyclerAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = (data == null ? new ArrayList<T>() : new ArrayList<T>(data));
    }

    public void add(T object) {
        mDatas.add(object);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public void refresh(List<T> data) {
        if (this.mDatas != null) {
            this.mDatas.clear();
            this.mDatas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void clean() {
        if (this.mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public void addTo(int location,T data){
        this.mDatas.add(location,data);
        notifyDataSetChanged();
    }

    public boolean contant(T data){
        return this.mDatas.contains(data);
    }

    public T getItemInPosition(int position){
        if(mDatas!=null&& mDatas.size()>position){
            return mDatas.get(position);
        }
        return null;
    }

    public List<T> getItems(){
        return mDatas;
    }

    public void removeItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(getItemLayoutId(), parent, false);
        return new BaseRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

        holder.itemView.setTag(position);
        T item = mDatas.get(position);

        onBindItemViewHolder(holder, item, position);
        onBindItemClick(holder, item, position);
    }

    /**
     * 获取适配器布局
     */
    protected abstract int getItemLayoutId();

    /**
     * 子类处理绑定item的viewholder数据
     */
    public abstract void onBindItemViewHolder(BaseRecyclerViewHolder holder, T item, int position);

    /**
     * 绑定点击事件
     */
    protected void onBindItemClick(final BaseRecyclerViewHolder holder, final T item,
                                   final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, item, holder.getAdapterPosition());
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(view, item, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnMultiItemClickListener(OnMultiItemClickListener<T> listener) {
        this.mOnMultiItemClickListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnItemLongClickListener = listener;
    }

    public static interface OnMultiItemClickListener<T> {
        public void onMultiItemClick(View view, T item, int position, int viewType);
    }

    public static interface OnItemClickListener<T> {
        public void onItemClick(View view, T item, int position);
    }

    public static interface OnItemLongClickListener<T> {
        public void onItemLongClick(View view, T item, int position);
    }
}
