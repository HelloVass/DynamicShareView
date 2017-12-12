package info.hellovass.dynamicdrawbitmap.library.core.rule;

import android.content.Context;
import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public class Rule2Impl extends AbsRule {

  public Rule2Impl(Context context) {
    super(context);
  }

  @Override
  public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    for (View child : children) {

      measureManually(child, screenWidth, (screenWidth - spacing) / 2);
    }
  }

  @Override
  public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    children[0].layout(0, 0, screenWidth, (screenWidth - spacing) / 2);
    children[1].layout(0, (screenWidth - spacing) / 2 + spacing, screenWidth, screenWidth);
  }
}
