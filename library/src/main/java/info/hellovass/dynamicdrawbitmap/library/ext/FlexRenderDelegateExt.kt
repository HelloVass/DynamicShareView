package info.hellovass.dynamicdrawbitmap.library.ext

import android.util.TypedValue
import info.hellovass.dynamicdrawbitmap.library.FlexRenderDelegate

internal val FlexRenderDelegate.coverCount: Int
    get() {
        return Math.min(FlexRenderDelegate.MAX_SIZE, repo.covers().size)
    }

internal val layout: IntArray
    get() {
        return intArrayOf(1, 3, 2, 1)
    }

internal val FlexRenderDelegate.spacing: Int
    get() {
        return dp2px(4.0F)
    }

internal fun FlexRenderDelegate.dp2px(dp: Float): Int {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
}
