package info.hellovass.dynamicdrawbitmap.library.core.rule;

import android.view.View;

/**
 * Created by hello on 2017/12/8.
 */

public interface Rule {

  void measureChildren(int screenWidth, int screenHeight, int spacing, View... children);

  void layoutChildren(int screenWidth, int screenHeight, int spacing, View... children);
}
