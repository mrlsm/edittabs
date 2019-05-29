package com.mrlsm.edittabs.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.mrlsm.edittabs.R;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class OtherGridView extends GridView {

    public OtherGridView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
