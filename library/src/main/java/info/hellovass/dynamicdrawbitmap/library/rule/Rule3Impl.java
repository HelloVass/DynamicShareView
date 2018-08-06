package info.hellovass.dynamicdrawbitmap.library.rule;

import android.content.Context;
import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public class Rule3Impl extends AbsRule {

  public Rule3Impl(Context context) {
    super(context);
  }

  @Override
  public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    measureManually1(children[0], (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
    measureManually1(children[1], (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
    measureManually1(children[2], screenWidth, (screenWidth - spacing) / 2);
  }

  @Override
  public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    children[0].layout(0, 0, (screenWidth - spacing) / 2, (screenWidth - spacing) / 2);
    children[1].layout((screenWidth - spacing) / 2 + spacing, 0, screenWidth,
        (screenWidth - spacing) / 2);
    children[2].layout(0, spacing + (screenWidth - spacing) / 2, screenWidth, screenWidth);
  }
}
