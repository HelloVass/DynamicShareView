package info.hellovass.dynamicdrawbitmap.library.utils;

import android.content.Context;
import info.hellovass.dynamicdrawbitmap.library.core.Adapter;

/**
 * Created by hello on 2017/12/9.
 */

public class Params {

  private final Context mContext;

  private final Adapter mAdapter;

  private final int mWidth;

  private final int mHeight;

  public Params(Builder builder) {

    mContext = builder.mContext;
    mAdapter = builder.mAdapter;
    mWidth = builder.mWidth;
    mHeight = builder.mHeight;
  }

  public Context getContext() {
    return mContext;
  }

  public Adapter getAdapter() {
    return mAdapter;
  }

  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }

  public static final class Builder {

    private final Context mContext;

    private final Adapter mAdapter;

    private int mWidth;

    private int mHeight;

    public Builder(Context context, Adapter adapter) {
      mContext = context;
      mAdapter = adapter;
    }

    public Builder setWidth(int width) {
      mWidth = width;
      return this;
    }

    public Builder setHeight(int height) {
      mHeight = height;
      return this;
    }

    public Params build() {

      checkEmptyFields();
      return new Params(this);
    }

    private void checkEmptyFields() {

      if (mWidth == 0) {
        mWidth = ScreenUtil.getScreenWidth(mContext);
      }

      if (mHeight == 0) {
        mHeight = ScreenUtil.getScreenHeight(mContext);
      }
    }
  }
}
