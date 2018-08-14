package info.hellovass.dynamicdrawbitmap.library

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import info.hellovass.dynamicdrawbitmap.library.ext.coverCount
import info.hellovass.dynamicdrawbitmap.library.ext.dp2px
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * 默认的渲染器
 */
class FlexRenderDelegate(internal val context: Context) : RenderDelegate {

    internal val flexboxLayout: FlexboxLayout

    internal val repo: IRepo = RepoImpl()

    companion object {
        const val MAX_SIZE = 20
    }

    init {
        val container = LayoutInflater.from(context).inflate(R.layout.layout_container, null)
        flexboxLayout = container.findViewById(R.id.flexboxLayout)
    }

    override fun getLayoutResId(): Int = R.layout.layout_container

    override fun render(): Observable<View> {

        return Observable.create<View> { emitter ->
            try {
                // 如果有封面，清空
                clearIfNeeded(flexboxLayout)
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

    private fun clearIfNeeded(container: ViewGroup) {

        if (container.childCount > 0) {
            container.removeAllViews()
        }
    }

    private fun addCovers() {

        for (index in 0 until coverCount) {

            val imageView = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                setBackgroundColor(Color.GRAY)
            }

            val lp = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT).apply {

                setMargins(dp2px(1.0F),
                        dp2px(1.0F),
                        dp2px(1.0F),
                        dp2px(1.0F)
                )

                flexGrow = 1.0F
            }

            flexboxLayout.addView(imageView, lp)
        }
    }

    private fun renderCovers() {

        for (position in 0 until coverCount) {
            val target = flexboxLayout.getChildAt(position) as ImageView
            val bitmap = loadImage(repo.covers()[position])
            loadCover(target, bitmap)
        }
    }

    private fun loadCover(target: ImageView, bitmap: Bitmap) {

        target.setImageBitmap(bitmap)
    }

    private fun loadImage(imageUrl: String): Bitmap {

        return Glide.with(context).asBitmap().load(imageUrl)
                .submit()
                .get()
    }
}


