package com.nan.nanlib.view.recycleradapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiannan
 * @date 2018/8/22
 */
public class BasePullComplexRecyclerAdapter<T> extends BaseSimpleRecyclerAdapter<T> {


    public static final int STATE_INVISIBLE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_ERROR = 4;

    protected int state = STATE_INVISIBLE;
    protected boolean isLoadingMore = false;
    protected AdapterDelegatesManager<T> delegatesManager = new AdapterDelegatesManager<T>();
    private boolean mCanLoadMore = false;
    private onLoadMoreListener loadMoreListener;
    public List<ItemView> footers = new ArrayList<>(1);

    public BasePullComplexRecyclerAdapter(RecyclerView recyclerView, Context context, List<T> data) {
        super(context, data);
        if (recyclerView!=null) {
            recyclerView.addOnScrollListener(new BasePullUpScrollListener());
        }
        addFooter(new Footer(context));
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + footers.size();
    }

    /**
     * 获取item个数
     */
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (footers.size() != 0) {
            int i = position - mDatas.size();
            if (i >= 0) {
                return footers.get(i).hashCode();
            }
        }
        return delegatesManager.getItemViewType(mDatas.get(position), position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View footerView = createSpViewByType(parent, viewType);
        if (footerView != null) {
            return new BaseRecyclerViewHolder(footerView);
        }
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    public View createSpViewByType(ViewGroup parent, int viewType) {

        for (ItemView footer : footers) {
            if (footer.hashCode() == viewType) {
                View view = footer.onCreateView(parent);
                return view;
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {

        holder.itemView.setTag(position);

        int i = position - mDatas.size();
        if (footers.size() != 0 && i >= 0) {
            footers.get(i).onBindView(holder.itemView);
            return;
        }

        T item = mDatas.get(position);

        delegatesManager.onBindViewHolder(item, position, holder);
        onBindItemClick(holder, item, position);
    }

    /**
     * 绑定点击事件
     */
    protected void onBindItemClick(final BaseRecyclerViewHolder holder, final T item,
                                   final int position) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, item, position);
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

    public void onBindItemViewHolder(BaseRecyclerViewHolder holder, T item, int position) {}

    protected int getItemLayoutId() {
        return 0;
    }


    public void addFooter(ItemView view) {
        if (view == null) {
            throw new NullPointerException("ItemView can't be null");
        }

        footers.add(view);
    }

    public void removeFooter(ItemView view) {
        footers.remove(view);
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    public void setCanLoadMore(boolean flag) {
        mCanLoadMore = flag;
        if (mCanLoadMore) {
            setState(STATE_LOADING);
        } else {
            setState(STATE_INVISIBLE);
        }
    }

    public void showNoMore() {
        mCanLoadMore = false;
        setState(STATE_NO_MORE);

    }

    public void showError() {
        mCanLoadMore = false;
        setState(STATE_ERROR);

    }

    /**
     * 滚动到底部时的监听器
     */
    public void setOnLoadMoreListener(onLoadMoreListener l) {
        this.loadMoreListener = l;
    }

    public void loadMoreCompleted() {
        isLoadingMore = false;
    }

    public interface ItemView {
        View onCreateView(ViewGroup parent);

        void onBindView(View footerView);
    }

    /**
     * 滚动到底部时的监听器
     */
    public interface onLoadMoreListener {
        void onLoadmore();
    }

    /**
     * 监听，判断RecyclerView滚动到底部时，加载onBottom方法
     */
    public class BasePullUpScrollListener extends RecyclerView.OnScrollListener {
        public static final int LINEAR = 0;
        public static final int GRID = 1;
        public static final int STAGGERED_GRID = 2;

        //标识RecyclerView的LayoutManager是哪种
        protected int layoutManagerType;
        // 瀑布流的最后一个的位置
        protected int[] lastPositions;
        // 最后一个的位置
        protected int lastVisibleItem;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

      /*LogUtil.info("test----",
          String.format("lastVisibleItem=%d," +
                  "footers.size()=%d," +
                  "total=%d" +
                  ",isloadmore=%s" +
                  ",canloadmore=%s", lastVisibleItem, footers.size(),
              getItemCount(), String.valueOf(isLoadingMore)
              , String.valueOf(mCanLoadMore)));*/

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + footers.size() ==
                    getItemCount()
                    && loadMoreListener != null
                    && !isLoadingMore
                    && mCanLoadMore) {

                isLoadingMore = true;
                loadMoreListener.onLoadmore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LINEAR;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are " +
                                "LinearLayoutManager, GridLayoutManager and " +
                                "StaggeredGridLayoutManager");
            }

            switch (layoutManagerType) {
                case LINEAR:
                    lastVisibleItem = ((LinearLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItem = ((GridLayoutManager) layoutManager)
                            .findLastVisibleItemPosition();
                    break;
                case STAGGERED_GRID:
                    StaggeredGridLayoutManager staggeredGridLayoutManager
                            = (StaggeredGridLayoutManager) layoutManager;
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItem = findMax(lastPositions);
                    break;
            }
        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }

    public int getFooterSize(){
        return footers.size();
    }

    private class Footer implements ItemView {

        FooterView footerView;

        public Footer(Context context) {
            footerView = new FooterView(context);
        }

        @Override
        public View onCreateView(ViewGroup parent) {
            return footerView;
        }

        @Override
        public void onBindView(View footerView) {
            refreshFooterView();
        }

        private void refreshFooterView() {

            switch (state) {
                case STATE_INVISIBLE:
                    footerView.setInVisibleState();
                    break;
                case STATE_LOADING:
                    footerView.setLoadingState();
                    break;
                case STATE_NO_MORE:
                    footerView.setNoMoreState();
                    break;
                case STATE_NO_DATA:
                    footerView.setNoDataState();
                    break;
                case STATE_ERROR:
                    footerView.showErrorState(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (loadMoreListener != null) {
                                setCanLoadMore(true);
                                loadMoreListener.onLoadmore();
                            }
                        }
                    });
                    break;
            }
        }
    }

    public List<T> getData(){
        return mDatas;
    }

}
