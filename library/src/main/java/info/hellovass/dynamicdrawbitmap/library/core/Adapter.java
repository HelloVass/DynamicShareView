package info.hellovass.dynamicdrawbitmap.library.core;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hello on 2017/12/8.
 */

public interface Adapter {

  /**
   * 获取封面个数
   */
  int getCoverCount();

  /**
   * 获取封面 View
   */
  View getImage(ViewGroup viewGroup, int position);

  /**
   * 获取底部 View
   */
  View getBottom(ViewGroup viewGroup);
}
