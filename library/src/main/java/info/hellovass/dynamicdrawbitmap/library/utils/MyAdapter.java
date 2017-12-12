package info.hellovass.dynamicdrawbitmap.library.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.zxing.WriterException;
import info.hellovass.dynamicdrawbitmap.library.R;
import info.hellovass.dynamicdrawbitmap.library.core.Adapter;
import info.hellovass.dynamicdrawbitmap.library.core.Bottom;
import info.hellovass.dynamicdrawbitmap.library.qrcode.QRCodeEncoder;
import java.util.List;

/**
 * Created by hello on 2017/12/11.
 */

public class MyAdapter implements Adapter {

  private List<String> mImages;

  private Bottom mBottom;

  private OnImageLoadedListener mOnImageLoadedListener;

  private int mLoadedImageCount = 0;

  public MyAdapter(List<String> images, @NonNull Bottom bottom,
      @NonNull OnImageLoadedListener onImageLoadedListener) {
    mImages = images;
    mBottom = bottom;
    mOnImageLoadedListener = onImageLoadedListener;
  }

  @Override public int getCoverCount() {

    return mImages.size();
  }

  @Override public View getImage(ViewGroup viewGroup, int position) {

    final View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.osl_ui_widgets_shareview_imageview_wrapper, viewGroup, false);

    Glide.with(view.getContext())
        .load(mImages.get(position))
        .asBitmap()
        .into(new SimpleTarget<Bitmap>() {

          @Override public void onResourceReady(Bitmap resource,
              GlideAnimation<? super Bitmap> glideAnimation) {

            ImageView ivImage = view.findViewById(R.id.iv_image);
            ivImage.setImageBitmap(resource);
            mLoadedImageCount++;

            if (mLoadedImageCount == getCoverCount() + 1) {
              mOnImageLoadedListener.onImageLoaded();
            }
          }
        });

    return view;
  }

  @Override public View getBottom(ViewGroup viewGroup) {

    final View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.osl_ui_widgets_shareview_bottom_part, viewGroup, false);

    TextView tvTitle = view.findViewById(R.id.tv_title);
    tvTitle.setText(mBottom.getTitle());

    Glide.with(viewGroup.getContext())
        .load(mBottom.getAvatarUrl())
        .asBitmap()
        .into(new SimpleTarget<Bitmap>() {

          @Override public void onResourceReady(Bitmap resource,
              GlideAnimation<? super Bitmap> glideAnimation) {

            ImageView ivAvatar = view.findViewById(R.id.iv_avatar);
            ivAvatar.setImageBitmap(resource);
            mLoadedImageCount++;

            if (mLoadedImageCount == getCoverCount() + 1) {
              mOnImageLoadedListener.onImageLoaded();
            }
          }
        });

    TextView tvNickname = view.findViewById(R.id.tv_nickname);
    tvNickname.setText(mBottom.getNickname());

    try {
      Bitmap qrCodeBitmap = QRCodeEncoder.encodeAsBitmap(mBottom.getQRCodeUrl(),
          DensityUtil.dip2px(viewGroup.getContext(), 60.0F));
      ImageView ivQRCode = view.findViewById(R.id.iv_qr_code);
      ivQRCode.setImageBitmap(qrCodeBitmap);
    } catch (WriterException e) {
      e.printStackTrace();
    }

    return view;
  }

  public interface OnImageLoadedListener {

    void onImageLoaded();
  }
}
