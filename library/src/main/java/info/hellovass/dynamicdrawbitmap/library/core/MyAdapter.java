package info.hellovass.dynamicdrawbitmap.library.core;

import android.content.Context;
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
import info.hellovass.dynamicdrawbitmap.library.entity.Bottom;
import info.hellovass.dynamicdrawbitmap.library.entity.Image;
import info.hellovass.dynamicdrawbitmap.library.qrcode.QRCodeEncoder;
import info.hellovass.dynamicdrawbitmap.library.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by hello on 2017/12/11.
 */

public class MyAdapter implements Adapter {

  private Context mContext;

  private List<Image> mImages;

  private Bottom mBottom;

  private OnImageLoadedListener mOnImageLoadedListener;

  private int mLoadedImageCount = 0;

  public MyAdapter(@NonNull Context context, List<Image> images, @NonNull Bottom bottom,
      @NonNull OnImageLoadedListener onImageLoadedListener) {
    mContext = context;
    mImages = images == null ? new ArrayList<Image>() : images;
    mBottom = bottom;
    mOnImageLoadedListener = onImageLoadedListener;
  }

  @Override public int getCoverCount() {

    return mImages.size();
  }

  private Image getCover(int position) {

    return mImages.get(position);
  }

  @Override public View getImage(ViewGroup viewGroup, int position) {

    final View view = LayoutInflater.from(mContext)
        .inflate(R.layout.osl_ui_widgets_shareview_imageview_wrapper, viewGroup, false);

    final View moreInfo = view.findViewById(R.id.ll_moreinfo_wrapper);
    final ImageView ivCover = view.findViewById(R.id.iv_image);

    // 付费图需要做高斯模糊处理
    final boolean needBlur = !getCover(position).isFree();

    if (needBlur) { // 如果需要高斯模糊处理

      Glide.with(viewGroup.getContext())
          .load(mImages.get(position).getImageUrl())
          .asBitmap()
          .transform(new BlurTransformation(viewGroup.getContext(), 14, 3))
          .into(new SimpleTarget<Bitmap>() {
            @Override public void onResourceReady(Bitmap bitmap,
                GlideAnimation<? super Bitmap> glideAnimation) {

              ivCover.setImageBitmap(bitmap);
              mLoadedImageCount++;

              if (mLoadedImageCount == getCoverCount() + 1) {
                mOnImageLoadedListener.onImageLoaded();
              }
            }
          });
    } else {

      Glide.with(viewGroup.getContext())
          .load(mImages.get(position).getImageUrl())
          .asBitmap()
          .into(new SimpleTarget<Bitmap>() {
            @Override public void onResourceReady(Bitmap bitmap,
                GlideAnimation<? super Bitmap> glideAnimation) {

              ivCover.setImageBitmap(bitmap);
              mLoadedImageCount++;

              if (mLoadedImageCount == getCoverCount() + 1) {
                mOnImageLoadedListener.onImageLoaded();
              }
            }
          });
    }


    moreInfo.setVisibility(!mImages.get(position).isFree() ? View.VISIBLE : View.GONE);

    return view;
  }

  @Override public View getBottom(ViewGroup viewGroup) {

    final View view = LayoutInflater.from(mContext)
        .inflate(R.layout.osl_ui_widgets_shareview_bottom_part, viewGroup, false);

    TextView tvTitle = view.findViewById(R.id.tv_title);
    tvTitle.setText(mBottom.getTitle());

    final ImageView ivAvatar = view.findViewById(R.id.iv_avatar);

    Glide.with(viewGroup.getContext())
        .load(mBottom.getAvatarUrl())
        .asBitmap()
        .into(new SimpleTarget<Bitmap>() {
          @Override public void onResourceReady(Bitmap resource,
              GlideAnimation<? super Bitmap> glideAnimation) {

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
