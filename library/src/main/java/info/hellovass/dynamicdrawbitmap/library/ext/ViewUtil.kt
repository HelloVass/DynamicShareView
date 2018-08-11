package info.hellovass.dynamicdrawbitmap.library.ext

import android.view.View

class ViewUtil {

    companion object {

        fun measure(target: View, width: Int) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                    View.MeasureSpec.EXACTLY)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 shl 30) - 1,
                    View.MeasureSpec.AT_MOST)
            target.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}