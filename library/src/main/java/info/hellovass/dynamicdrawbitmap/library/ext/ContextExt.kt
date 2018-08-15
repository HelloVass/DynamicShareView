package info.hellovass.dynamicdrawbitmap.library.ext

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue


/**
 * 得到屏幕的宽度
 *
 * @return 屏幕的宽度，单位像素
 */
internal fun Context.screenWidth(): Int {
    val displayMetrics = this.resources.displayMetrics
    return displayMetrics.widthPixels
}

/**
 * 将 dp 转为 px
 */
internal fun Context.dp2px(dp: Float): Int {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
            .toInt()
}

/**
 * 获取字体图标
 */
internal fun Context.iconfont(): Typeface {

    return Typeface.createFromAsset(assets, "iconfont.ttf")
}

