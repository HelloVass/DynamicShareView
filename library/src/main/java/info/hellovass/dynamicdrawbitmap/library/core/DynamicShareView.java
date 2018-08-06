package info.hellovass.dynamicdrawbitmap.library.core;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import info.hellovass.dynamicdrawbitmap.library.rule.BottomRule;
import info.hellovass.dynamicdrawbitmap.library.rule.Rule;
import info.hellovass.dynamicdrawbitmap.library.rule.Rule1Impl;
import info.hellovass.dynamicdrawbitmap.library.rule.Rule2Impl;
import info.hellovass.dynamicdrawbitmap.library.rule.Rule3Impl;
import info.hellovass.dynamicdrawbitmap.library.rule.Rule4Impl;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hello on 2017/12/8.
 */

public class DynamicShareView extends ViewGroup {

  private static final int MAX_COUNT = 4;

  private int mSpacing = dp2px(1.0F); // 默认间隔1dp

  private Adapter mAdapter;

  private List<View> mImages = new ArrayList<>(MAX_COUNT);

  private View mBottomLayout;

  private int mActualWidth;

  private int mActualHeight;

  private Rule mRule1;

  private Rule mRule2;

  private Rule mRule3;

  private Rule mRule4;

  private Rule mBottomRule;

  public DynamicShareView(Context context) {
    super(context);
    init();
  }

  public DynamicShareView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {

    setBackgroundColor(Color.parseColor("#FFFFFF")); // 设置背景颜色，不然截图的时候背景色是黑的

    mRule1 = new Rule1Impl(getContext());
    mRule2 = new Rule2Impl(getContext());
    mRule3 = new Rule3Impl(getContext());
    mRule4 = new Rule4Impl(getContext());
    mBottomRule = new BottomRule(getContext());
  }

  public void setAdapter(@NonNull Adapter adapter) {

    mAdapter = adapter;

    removeAllViews(); // 移除所有子View

    // 添加图片
    int imageCount = Math.min(mAdapter.getCoverCount(), MAX_COUNT);
    for (int index = 0; index < imageCount; index++) {

      View image = mAdapter.getImage(this, index);
      addView(image);
      mImages.add(image);
    }

    // 添加底部
    View bottomLayout = mAdapter.getBottom(this);
    mBottomLayout = bottomLayout;
    addView(bottomLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int widthSize = MeasureSpec.getSize(widthMeasureSpec);

    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    mActualWidth = widthSize; // 宽度等于屏幕宽度

    switch (mImages.size()) {

      case 1:
        mRule1.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0));
        break;

      case 2:
        mRule2.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0),
            mImages.get(1));
        break;

      case 3:
        mRule3.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0),
            mImages.get(1), mImages.get(2));
        break;

      case 4:
        mRule4.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0),
            mImages.get(1), mImages.get(2), mImages.get(3));
        break;
    }

    // 测量底部高度
    mBottomRule.measureChildren(mActualWidth, mActualHeight, mSpacing, mBottomLayout);

    // 获得实际高度
    mActualHeight = mActualWidth + mBottomLayout.getMeasuredHeight();

    // 设置 ViewGroup 尺寸
    setMeasuredDimension(mActualWidth,
        heightMode == MeasureSpec.EXACTLY ? heightSize : mActualHeight);
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

    if (mImages == null || mImages.isEmpty()) return;

    switch (mImages.size()) {

      case 1:
        mRule1.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0));
        break;

      case 2:
        mRule2.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0),
            mImages.get(1));
        break;

      case 3:
        mRule3.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0), mImages.get(1),
            mImages.get(2));
        break;

      case 4:
        mRule4.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages.get(0), mImages.get(1),
            mImages.get(2), mImages.get(3));
        break;
    }

    mBottomRule.layoutChildren(mActualWidth, mActualHeight, mSpacing, mBottomLayout);
  }

  private int dp2px(float dp) {

    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        getResources().getDisplayMetrics());
  }
}
