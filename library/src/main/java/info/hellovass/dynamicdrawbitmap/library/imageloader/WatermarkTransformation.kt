package info.hellovass.dynamicdrawbitmap.library.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import jp.wasabeef.glide.transformations.BitmapTransformation
import jp.wasabeef.glide.transformations.internal.Utils
import java.security.MessageDigest

/**
 * 添加水印标识
 */
class WatermarkTransformation(private val watermarkId: Int) : BitmapTransformation() {

    companion object {

        private const val VERSDON = 1

        private const val ID: String =
                "info.hellovass.dynamicdrawbitmap.library.imageloader.$VERSDON"

        private val paint: Paint = Paint().apply {

            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
        }
    }

    override fun transform(context: Context, pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {

        // 原图的宽高
        val width = toTransform.width
        val height = toTransform.height

        // 缓存
        val bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888)

        // 水印
        val watermarkDrawable: Drawable =
                Utils.getMaskDrawable(context.applicationContext, watermarkId)

        // 水印的宽高
        val watermarkWidth = watermarkDrawable.intrinsicWidth
        val watermarkHeight = watermarkDrawable.intrinsicHeight

        // 计算出水印的坐标
        val left: Int = (width - watermarkWidth) / 2
        val top: Int = (height - watermarkHeight) / 2
        val right: Int = left + watermarkWidth
        val bottom: Int = top + watermarkHeight

        // 先画原图
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(toTransform, 0.0F, 0.0F, paint)

        // 再画水印
        watermarkDrawable.setBounds(left, top, right, bottom)
        watermarkDrawable.draw(canvas)

        return bitmap
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
}