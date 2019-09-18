package com.nan.nanlib.view.recycleradapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.ContentLoadingProgressBar;

import com.nan.nanlib.view.R;


/**
 * @author liujiannan
 * @date 2018/8/27
 */
public class FooterView extends RelativeLayout {


    protected String loadingText = "加载中…";
    protected String noMoreText = "没有更多内容了";
    protected String noDataText = "暂无内容";
    protected String errorText = "加载失败,点击重试";

    private TextView textView;
    private ProgressBar progressBar;

    public FooterView(Context context) {
        super(context);
        init(context);
    }

    /**
     * dip to px
     */
    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public void init(Context context) {
        inflate(context, R.layout.xm_comm_view_footer, this);
        setGravity(Gravity.CENTER);
        textView = (TextView) findViewById(R.id.footer_content);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.footer_progressbar);
    }

    public void setText(CharSequence s) {
        textView.setText(s);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setTextSize(float size) {
        textView.setTextSize(size);
    }

    public void setNoDataState() {
        this.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(noDataText);
        textView.setClickable(false);
    }

    public void setNoMoreState() {
        this.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(noMoreText);
        textView.setClickable(false);
    }

    public void setLoadingState() {
        this.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(loadingText);
        textView.setClickable(false);

    }

    public void showErrorState(OnClickListener listener) {
        this.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(errorText);
        textView.setClickable(true);
        textView.setOnClickListener(listener);
    }

    public void setInVisibleState() {
        this.setVisibility(View.GONE);
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public void setNoMoreText(String noMoreText) {
        this.noMoreText = noMoreText;
    }

    public void setNoDataText(String noDataText) {
        this.noDataText = noDataText;
    }


}
