package info.hellovass.dynamicdrawbitmap.library.ext

import android.util.TypedValue
import info.hellovass.dynamicdrawbitmap.library.FlexRenderDelegate

internal val FlexRenderDelegate.coverCount: Int
    get() {
        return Math.min(FlexRenderDelegate.MAX_SIZE, repo.covers().size)
    }

internal val FlexRenderDelegate.layout: IntArray
    get() =
        when (coverCount) {
            1 -> {
                intArrayOf(1)
            }
            2 -> {
                intArrayOf(1, 1)
            }
            3 -> {
                intArrayOf(1, 2)
            }
            4 -> {
                intArrayOf(1, 3)
            }
            5 -> {
                intArrayOf(1, 3, 1)
            }
            6 -> {
                intArrayOf(1, 3, 2)
            }
            else -> {
                intArrayOf(1, 3, 2, 1)
            }
        }

internal val FlexRenderDelegate.spacing: Int
    get() {
        return dp2px(4.0F)
    }

internal fun FlexRenderDelegate.dp2px(dp: Float): Int {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
}
