package info.hellovass.dynamicdrawbitmap.library.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import info.hellovass.dynamicdrawbitmap.library.ext.iconfont
import info.hellovass.dynamicdrawbitmap.library.ext.sp2px
import jp.wasabeef.glide.transformations.BitmapTransformation
import java.security.MessageDigest

/**
 * 添加水印标识
 */
class WatermarkTransformation(private val watermarkId: Int) : BitmapTransformation() {

    companion object {

        private const val VERSDON = 1

        private const val ID: String =
                "info.hellovass.dynamicdrawbitmap.library.imageloader.$VERSDON"
    }

    // 原图的尺寸
    private lateinit var srcSize: Pair<Int, Int>

    // 画笔
    private lateinit var textPaint: TextPaint

    override fun transform(context: Context, pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {

        // 缓存
        val cachedBitmap = pool.get(toTransform.width, toTransform.height,
                Bitmap.Config.ARGB_8888)

        // 原图的宽高
        srcSize = Pair(toTransform.width, toTransform.height)

        // 画笔初始化
        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.BLACK

            typeface = context.iconfont()

            isDither = true

            style = Paint.Style.FILL

            textSize = context.sp2px(18.0F)
        }

        // 创建 canvas
        val canvas = Canvas(cachedBitmap)

        // step1.绘制原图
        drawSrc(canvas, toTransform)

        // step2.绘制水印
        drawWatermark(canvas, context.getString(watermarkId))

        // 返回 cached Bitmap
        return cachedBitmap
    }

    override fun equals(other: Any?): Boolean {

        return other is WatermarkTransformation
                && other.watermarkId == watermarkId
    }

    override fun hashCode(): Int {

        return ID.hashCode() + watermarkId * 10
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

        messageDigest.update((ID + watermarkId).toByteArray(Key.CHARSET))
    }

    private fun drawSrc(canvas: Canvas, toTransform: Bitmap) {

        canvas.drawBitmap(toTransform, 0.0F, 0.0F, null)
    }

    private fun drawWatermark(canvas: Canvas, text: String = "\uE614") {

        fun calLeft(): Float = srcSize.first / 2.0F - textPaint.measureText(text) / 2.0F

        fun calTop(): Float {
            val fontMetrics = textPaint.fontMetrics
            return srcSize.second / 2.0F + (fontMetrics.descent - fontMetrics.ascent) / 2.0F - fontMetrics.bottom
        }

        canvas.drawText(text, calLeft(), calTop(), textPaint)
    }
}


