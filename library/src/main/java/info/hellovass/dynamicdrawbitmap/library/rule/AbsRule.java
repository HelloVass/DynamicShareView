package info.hellovass.dynamicdrawbitmap.library.rule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public abstract class AbsRule implements Rule {

  private Context mContext;

  public AbsRule(@NonNull Context context) {
    mContext = context;
  }

  protected int dp2px(float dp) {

    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        mContext.getResources().getDisplayMetrics());
  }

  protected void measureManually1(View target, int widthSize, int heightSize) {

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY);
    target.measure(widthMeasureSpec, heightMeasureSpec);
  }

  protected void measureManually2(View target, int widthSize) {

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
    int heightMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
    target.measure(widthMeasureSpec, heightMeasureSpec);
  }
}
