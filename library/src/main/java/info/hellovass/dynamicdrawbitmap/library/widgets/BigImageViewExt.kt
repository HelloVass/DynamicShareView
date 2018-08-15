package info.hellovass.dynamicdrawbitmap.library.widgets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.TypedValue
import java.io.InputStream

fun BigImageView.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}


fun calImageSize(stream: InputStream?): BigImageView.Size {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeStream(stream, null, options)
    return BigImageView.Size(width = options.outWidth, height = options.outHeight)
}

fun BigImageView.drawRegion(canvas: Canvas?) {

    val bitmap: Bitmap? = bitmapRegionDecoder?.decodeRegion(rect, options)

    canvas?.let { it ->
        bitmap?.let { bitmap ->
            it.drawBitmap(bitmap, 0.0F, 0.0F, null)
        }
    }
}
