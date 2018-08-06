package info.hellovass.dynamicdrawbitmapsample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.view.View;
import info.hellovass.dynamicdrawbitmap.library.utils.DensityUtil;
import info.hellovass.dynamicdrawbitmap.library.utils.ScreenUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hello on 2017/12/8.
 */

public final class DynamicShareViewTool {

  private Context mContext;

  private String mSavePath;

  public static DynamicShareViewTool create(Context context) {
    return new DynamicShareViewTool(context);
  }

  private DynamicShareViewTool(Context context) {
    mContext = context;
  }

  public void share(View target) {

    measureManually(target, ScreenUtil.getScreenWidth(mContext),
        ScreenUtil.getScreenWidth(mContext) + DensityUtil.dip2px(mContext, 175.0F));
    target.layout(0, 0, target.getMeasuredWidth(), target.getMeasuredHeight());

    //  生成Bitmap
    Bitmap bitmap = generateBitmap(target, target.getMeasuredWidth(), target.getMeasuredHeight());

    //  将 Bitmap 保存为临时文件
    try {
      saveBitmap(bitmap);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    mContext.startActivity(createShareIntent());
  }

  /**
   * 生成Bitmap
   */
  private Bitmap generateBitmap(View target, int width, int height) {

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // 创建 Bitmap
    target.draw(new Canvas(bitmap)); // 绘制在 Bitmap 上
    return bitmap;
  }

  private void saveBitmap(Bitmap bitmap) throws IOException {

    FileOutputStream fileOutputStream = new FileOutputStream(createTempFile());
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
    fileOutputStream.flush();
    fileOutputStream.close();
  }

  private File createTempFile() throws IOException {

    File file = File.createTempFile("pero_share_image_", ".jpg", mContext.getCacheDir());
    mSavePath = file.getAbsolutePath(); // 保存路径
    return file;
  }

  private Intent createShareIntent() {

    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.setType("image/*");
    shareIntent.putExtra(Intent.EXTRA_STREAM, getUriFromFile());
    return shareIntent;
  }

  private Uri getUriFromFile() {

    return FileProvider.getUriForFile(mContext, "info.hellovass.fileprovider", new File(mSavePath));
  }

  private void measureManually(View target, int widthSize, int heightSize) {
    int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, View.MeasureSpec.EXACTLY);
    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, View.MeasureSpec.EXACTLY);
    target.measure(widthMeasureSpec, heightMeasureSpec);
  }
}
