package info.hellovass.dynamicdrawbitmap.library.core.rule;

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

  protected void measureManually(View target, int widthSize, int heightSize) {

    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY);
    target.measure(widthMeasureSpec, heightMeasureSpec);
  }
}
