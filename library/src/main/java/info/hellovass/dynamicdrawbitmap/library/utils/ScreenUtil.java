package info.hellovass.dynamicdrawbitmap.library.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by HelloVass on 16/3/3.
 */
public final class ScreenUtil {

  private ScreenUtil() {

  }

  /**
   * 得到屏幕的宽度
   *
   * @param context 上下文
   * @return 屏幕的宽度，单位像素
   */
  public static int getScreenWidth(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return displayMetrics.widthPixels;
  }

  /**
   * 得到屏幕的高度
   *
   * @param context 上下文
   * @return 屏幕的高度，单位像素
   */
  public static int getScreenHeight(Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    return displayMetrics.heightPixels;
  }

  public static int getScreenDensity(Context context) {

    return context.getResources().getDisplayMetrics().densityDpi;
  }

  public static void hideSystemUI(Activity activity) {

    activity.getWindow()
        .getDecorView()
        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_FULLSCREEN);
  }

  public static void showSystemUI(Activity activity) {

    activity.getWindow()
        .getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
  }
}

