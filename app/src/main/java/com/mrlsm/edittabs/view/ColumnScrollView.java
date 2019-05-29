package com.mrlsm.edittabs.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class ColumnScrollView extends HorizontalScrollView {
    /**
     * 传入整体布局
     */
    private View ll_content;
    /**
     * 左阴影图片
     */
    private ImageView leftImage;
    /**
     * 右阴影图片
     */
    private ImageView rightImage;
    /**
     * 父类的活动activity
     */
    private Activity activity;

    public ColumnScrollView(Context context) {
        super(context);
    }

    public ColumnScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        super.onScrollChanged(left, top, oldLeft, oldTop);
        if (!activity.isFinishing() && ll_content != null) {
            //如果整体宽度小于屏幕宽度的话，那左右阴影都隐藏
            if (ll_content.getWidth() <= getMeasuredWidth()) {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.GONE);
                return;
            }
            //如果滑动在最左边时候，左边阴影隐藏，右边显示
            if (left == 0) {
                leftImage.setVisibility(View.GONE);
                rightImage.setVisibility(View.VISIBLE);
                return;
            }
            //如果滑动在最右边时候，左边阴影显示，右边隐藏
            if (left == ll_content.getWidth() - getMeasuredWidth()) {
                leftImage.setVisibility(View.VISIBLE);
                rightImage.setVisibility(View.GONE);
                return;
            }
            //否则，说明在中间位置，左、右阴影都显示
            leftImage.setVisibility(View.VISIBLE);
            rightImage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 传入父类布局中的资源文件
     */
    public void setParam(Activity activity, View content, ImageView left, ImageView right) {
        this.activity = activity;
        ll_content = content;
        leftImage = left;
        rightImage = right;
    }
}
