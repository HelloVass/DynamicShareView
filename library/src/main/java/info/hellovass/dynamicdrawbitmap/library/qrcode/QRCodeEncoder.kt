package info.hellovass.dynamicdrawbitmap.library.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class QRCodeEncoder {

    companion object {
        const val BLACK = Color.BLACK
        const val WHITE = Color.WHITE
    }

    fun encodeAsBitmap(
            content: String,
            charset: String = "UTF-8",
            errorLevel: String = "H",
            width: Int = 96,
            height: Int = 96,
            margin: String = "0"
    ): Bitmap? {
        try {
            val hintMap: MutableMap<EncodeHintType, Any> = HashMap()

            // 字符编码支持
            hintMap[EncodeHintType.CHARACTER_SET] = charset

            // 容错级别
            hintMap[EncodeHintType.ERROR_CORRECTION] = errorLevel

            // 边距
            hintMap[EncodeHintType.MARGIN] = margin

            val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hintMap)

            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (matrix[x, y]) BLACK else WHITE
                }
            }

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: Throwable) {
            e.printStackTrace()
            return null
        }
    }
}