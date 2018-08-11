package info.hellovass.dynamicdrawbitmap.library.ext

import android.content.Context


/**
 * 得到屏幕的宽度
 *
 * @param context 上下文
 * @return 屏幕的宽度，单位像素
 */
internal fun Context.getScreenWidth(): Int {
    val displayMetrics = this.resources.displayMetrics
    return displayMetrics.widthPixels
}

