package info.hellovass.dynamicdrawbitmap.library.ext

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * 生成Bitmap
 */
internal fun View.createBitmap(bgColor: Int): Bitmap {

    // 设置背景色
    setBackgroundColor(bgColor)

    // 创建 Bitmap
    val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight,
            Bitmap.Config.ARGB_8888)
    // 绘制在 Bitmap 上
    this.draw(Canvas(bitmap))
    // 完事
    return bitmap
}

internal fun View.measureMually(width: Int = (1 shl 30) - 1, height: Int = (1 shl 30) - 1) {
    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
            View.MeasureSpec.EXACTLY)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
            View.MeasureSpec.AT_MOST)
    this.measure(widthMeasureSpec, heightMeasureSpec)
}