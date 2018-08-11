package info.hellovass.dynamicdrawbitmap.library

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import info.hellovass.dynamicdrawbitmap.library.ext.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * 默认的渲染器
 */
class FlexRenderDelegate(internal val context: Context) : RenderDelegate {

    internal val flexboxLayout: FlexboxLayout

    internal val repo: IRepo = RepoImpl()

    companion object {
        const val MAX_SIZE = 7
    }

    init {
        val container = LayoutInflater.from(context).inflate(R.layout.layout_container, null)
        flexboxLayout = container.findViewById(R.id.flexboxLayout)
        println()
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
                renderCovers(context.getScreenWidth())
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
            flexboxLayout.addView(ImageView(context))
        }
    }

    private fun renderCovers(screenWidth: Int) {

        var position = 0

        for (i in 0 until layout.size) {
            // 计算出图片宽度
            val width = (screenWidth - spacing * (layout[i] - 1)) / layout[i]

            for (j in 0 until layout[i]) {

                val target = flexboxLayout.getChildAt(position) as ImageView
                val bitmap = loadImage(repo.covers()[position])

                resizeCover(target, width, calRatio(bitmap))
                loadCover(target, bitmap)
                position++
            }
        }
    }

    private fun calRatio(bitmap: Bitmap): Float {

        return bitmap.width.toFloat() / bitmap.height.toFloat()
    }

    private fun resizeCover(imageView: ImageView, width: Int, ratio: Float) {

        imageView.apply {
            layoutParams.width = width
            layoutParams.height = (width / ratio).toInt()
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


