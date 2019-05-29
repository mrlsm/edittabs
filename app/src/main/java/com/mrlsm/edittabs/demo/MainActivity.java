package com.mrlsm.edittabs.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mrlsm.edittabs.R;
import com.mrlsm.edittabs.adapter.DragAdapter;
import com.mrlsm.edittabs.adapter.OtherAdapter;
import com.mrlsm.edittabs.data.ChannelItem;
import com.mrlsm.edittabs.data.ChannelManage;
import com.mrlsm.edittabs.view.ColumnScrollView;
import com.mrlsm.edittabs.view.CrossView;
import com.mrlsm.edittabs.view.DragGrid;
import com.mrlsm.edittabs.view.OtherGridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Mrlsm
 * @since 2019/5/29
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.column_horizontal_scroll_view)
    ColumnScrollView mColumnScrollView;
    @BindView(R.id.radio_group_content)
    LinearLayout mRadioGroupContent;
    @BindView(R.id.channel_user_name)
    RelativeLayout channelUserName;
    @BindView(R.id.edit_tab_layout)
    LinearLayout editTabLayout;
    @BindView(R.id.rl_column)
    RelativeLayout rlColumn;
    @BindView(R.id.button_more_columns)
    CrossView buttonMoreColumns;
    @BindView(R.id.shade_left)
    ImageView shadeLeft;
    @BindView(R.id.shade_right)
    ImageView shadeRight;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.edit_text)
    TextView mEditText;

    @BindView(R.id.user_grid_view)
    DragGrid userGridView;
    DragAdapter userAdapter;
    @BindView(R.id.other_grid_view)
    OtherGridView otherGridView;
    OtherAdapter otherAdapter;

    private int columnSelectIndex = 0;
    private int mItemWidth = 0;
    private int mScreenWidth = 0;

    private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    private ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    private ArrayList<DemoFragment> fragments = new ArrayList<>();

    private static final String TEXT_EDIT = "编辑";
    private static final String TEXT_FINISH = "完成";
    /**
     * 是否在移动，由于是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
     */
    boolean isMove = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mScreenWidth = getWindowsWidth(this);
        mItemWidth = mScreenWidth / 7; // 一个Item宽度为屏幕的1/7
        initView();
    }

    private void initEditLayoutData() {
        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(otherAdapter);
        //设置点击监听
        userGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);
                    if (TextUtils.equals(mEditText.getText(), TEXT_FINISH) && channel.isCanEdit()) {
                        //添加到最后一个
                        otherAdapter.setVisible(false);
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    } else {
                        columnSelectIndex = position;
                        buttonMoreColumns.toggle();
                        changeView(false);
                        initTabColumn();
                        initFragment();
                        mViewPager.setCurrentItem(position);
                    }
                }
            }
        });
        otherGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation, endLocation, otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
            }
        });
        mEditText.setText(TEXT_EDIT);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAdapter.setEditState(TextUtils.equals(mEditText.getText(), TEXT_EDIT));
                userAdapter.notifyDataSetChanged();
                mEditText.setText(TextUtils.equals(mEditText.getText(), TEXT_EDIT) ? TEXT_FINISH : TEXT_EDIT);
            }
        });
    }

    /**
     * 获取屏幕的宽度
     */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void initView() {
        buttonMoreColumns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonMoreColumns.getState() == CrossView.FLAG_STATE_PLUS) {
                    changeView(true);
                    initEditLayoutData();
                } else {
                    changeView(false);
                    setChannelView();
                }
                buttonMoreColumns.toggle();
            }
        });
        setChannelView();
    }

    private void changeView(boolean state) {
        editTabLayout.setVisibility(state ? View.VISIBLE : View.GONE);
        channelUserName.setVisibility(state ? View.VISIBLE : View.GONE);
        rlColumn.setVisibility(!state ? View.VISIBLE : View.GONE);
        mViewPager.setVisibility(!state ? View.VISIBLE : View.GONE);
    }

    /**
     * 当栏目项发生变化时候调用
     */
    private void setChannelView() {
        initColumnData();
        initTabColumn();
        initFragment();
    }

    /**
     * 获取Column栏目 数据
     */
    private void initColumnData() {
        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage().getUserChannel());
        otherChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage().getOtherChannel());
    }

    /**
     * 初始化Column栏目项
     */
    @SuppressLint("ResourceType")
    private void initTabColumn() {
        mRadioGroupContent.removeAllViews();
        int count = userChannelList.size();
        mColumnScrollView.setParam(this, mRadioGroupContent, shadeLeft, shadeRight);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            TextView columnTextView = new TextView(this);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(userChannelList.get(i).getName());
            columnTextView.setTextColor(getResources().getColorStateList(R.drawable.edit_bar_text_color));
            columnTextView.setSelected(columnSelectIndex == i);

            // 单击监听
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroupContent.getChildCount(); i++) {
                        View localView = mRadioGroupContent.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                        } else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroupContent.addView(columnTextView, i, params);
        }
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments.clear();
        int count = userChannelList.size();
        for (int i = 0; i < count; i++) {
            DemoFragment fragment = new DemoFragment();
            fragment.setText(userChannelList.get(i).name);
            fragments.add(fragment);
        }
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(pageListener);
    }

    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_position) {
        columnSelectIndex = tab_position;
        for (int i = 0; i < mRadioGroupContent.getChildCount(); i++) {
            View checkView = mRadioGroupContent.getChildAt(tab_position);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnScrollView.smoothScrollTo(i2, 0);
        }
        //判断是否选中
        for (int j = 0; j < mRadioGroupContent.getChildCount(); j++) {
            View checkView = mRadioGroupContent.getChildAt(j);
            checkView.setSelected(j == tab_position);
        }
    }

    /**
     * 点击ITEM移动动画
     */
    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }
}
