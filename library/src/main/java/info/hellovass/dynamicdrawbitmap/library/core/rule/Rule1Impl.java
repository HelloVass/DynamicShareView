package info.hellovass.dynamicdrawbitmap.library.core.rule;

import android.content.Context;
import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public class Rule1Impl extends AbsRule {

  public Rule1Impl(Context context) {
    super(context);
  }

  @Override
  public void measureChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    measureManually(children[0], screenWidth, screenWidth);
  }

  @Override
  public void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children) {

    children[0].layout(0, 0, screenWidth, screenWidth);
  }
}
