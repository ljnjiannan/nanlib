package com.nan.nanlib.view.recycleradapter;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews = new SparseArray<>();

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    public <T extends View> T findView(int resId) {
        View childView = mViews.get(resId);
        if (null == childView) {
            childView = itemView.findViewById(resId);
            mViews.put(resId, childView);
        }
        return (T) childView;
    }

    /**
     * 设置textview的内容
     */
    public BaseRecyclerViewHolder setText(int resId, String text) {
        TextView textView = findView(resId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置图片资源
     */
    public BaseRecyclerViewHolder setImageResource(int resId, int drawableId) {
        ImageView imageView = findView(resId);
        imageView.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片资源
     */
    public BaseRecyclerViewHolder setImageBitmap(int resId, Bitmap bitmap) {
        ImageView imageView = findView(resId);
        imageView.setImageBitmap(bitmap);
        return this;
    }
}
