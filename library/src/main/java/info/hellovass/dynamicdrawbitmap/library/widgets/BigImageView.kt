package info.hellovass.dynamicdrawbitmap.library.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

interface IBigImageView {
    fun loadImage(filePath: String?)
}

/**
 * support display long image
 */
class BigImageView : View, IBigImageView {

    internal var bitmapRegionDecoder: BitmapRegionDecoder? = null

    private var size: Size = Size(0, 0)

    internal var rect: Rect = Rect()

    internal val options = with(BitmapFactory.Options()) {
        inPreferredConfig = Bitmap.Config.ARGB_8888
        this
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    /**
     * 尺寸数据类
     */
    data class Size(val width: Int, val height: Int)

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // 使用默认方式测量尺寸
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        rect.apply {
            left = size.width / 2 - measuredWidth / 2
            top = size.height / 2 - measuredHeight / 2
            right = left + measuredWidth
            bottom = top + measuredHeight
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawRegion(canvas)
    }

    override fun loadImage(filePath: String?) {

        var stream: InputStream? = null

        try {
            stream = FileInputStream(File(filePath))
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(stream, false)
            size = calImageSize(stream)

            // 刷新
            invalidate()
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            stream?.close()
        }
    }
}