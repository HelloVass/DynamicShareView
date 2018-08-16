package info.hellovass.dynamicdrawbitmap.library.core

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayout
import info.hellovass.dynamicdrawbitmap.library.R
import info.hellovass.dynamicdrawbitmap.library.ext.dp2px
import info.hellovass.dynamicdrawbitmap.library.imageloader.GlideLoader
import info.hellovass.dynamicdrawbitmap.library.imageloader.ImageLoader
import info.hellovass.dynamicdrawbitmap.library.imageloader.WatermarkTransformation
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.BlurTransformation

class CoversRedner(private val context: Context) {

    companion object {
        // 最大图片数
        const val MAX_SIZE = 7
    }

    private val flexboxLayout: FlexboxLayout by lazy {
        inflate() as FlexboxLayout
    }

    private val imageLoader: ImageLoader by lazy {
        GlideLoader()
    }

    private val repo: CoversRepo by lazy {
        CoversRepo()
    }

    private val coverCount by lazy {
        Math.min(repo.covers().size, CoversRedner.MAX_SIZE)
    }

    fun render(): Observable<View> {

        return Observable.create<View> { emitter ->
            try {
                // 如果有子View，清空
                clear()
                // 添加封面
                addCovers()
                // 渲染封面
                renderCovers()
                // 渲染成功
                emitter.onNext(flexboxLayout)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun clear() {

        if (flexboxLayout.childCount > 0) {
            flexboxLayout.removeAllViews()
        }
    }

    private fun addCovers() {

        for (index in 0 until coverCount) {

            val target = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            val lp = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT).apply {

                setMargins(context.dp2px(1.0F),
                        context.dp2px(1.0F),
                        context.dp2px(1.0F),
                        context.dp2px(1.0F)
                )
                flexGrow = 1.0F
            }

            flexboxLayout.addView(target, lp)
        }
    }

    private fun renderCovers() {

        for (position in 0 until coverCount) {

            val target = flexboxLayout.getChildAt(position) as ImageView
            val bitmap = imageLoader.loadImage(context, repo.covers()[position], needBlur(position))

            target.apply {
                setImageBitmap(bitmap)
            }
        }
    }

    private fun needBlur(position: Int): RequestOptions {

        // 如果是最后一张图，进行高斯模糊和水印处理
        return if (position == CoversRedner.MAX_SIZE - 1) {
            RequestOptions().transforms(BlurTransformation(),
                    WatermarkTransformation(R.drawable.ic_more))
        } else {
            RequestOptions.centerCropTransform()
        }
    }

    @SuppressLint("InflateParams")
    private fun inflate(): View = LayoutInflater.from(context).inflate(R.layout.layout_covers, null)
}

