package info.hellovass.dynamicdrawbitmap.library.rule;

import android.content.Context;
import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public class Rule4Impl extends AbsRule {

  public Rule4Impl(Context context) {
    super(context);
  }

  @Override
  public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    for (View child : children) {

      measureManually1(child, (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
    }
  }

  @Override
  public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    for (int index = 0; index < children.length; index++) {

      View child = children[index];

      int rowNum = index / 2;
      int columnNum = index % 2;

      int left = (child.getMeasuredWidth() + spacing) * columnNum;
      int top = (child.getMeasuredHeight() + spacing) * rowNum;
      int right = left + child.getMeasuredWidth();
      int bottom = top + child.getMeasuredHeight();

      child.layout(left, top, right, bottom);
    }
  }
}
