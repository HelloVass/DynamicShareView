package info.hellovass.dynamicdrawbitmap.library.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class CircleImageView : ImageView {

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

    }
}