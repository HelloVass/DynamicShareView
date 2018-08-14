package info.hellovass.dynamicdrawbitmap.library.ext

import android.util.TypedValue
import info.hellovass.dynamicdrawbitmap.library.FlexRenderDelegate

internal val FlexRenderDelegate.coverCount: Int
    get() {
        return Math.min(FlexRenderDelegate.MAX_SIZE, repo.covers().size)
    }

internal fun FlexRenderDelegate.dp2px(dp: Float): Int {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
            .toInt()
}
