package info.hellovass.dynamicdrawbitmap.library

import android.view.View

class ViewUtil {

    companion object {

        fun measureExactly(target: View, width: Int, height: Int) {

            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
            target.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}