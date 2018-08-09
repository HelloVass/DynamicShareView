package info.hellovass.dynamicdrawbitmap.library

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class ShareView : ViewGroup, IShareView {

    companion object {
        const val MAX_COUNT = 4
    }

    private var actualWidth: Int = 0

    private var actutalHeight: Int = 0

    private lateinit var adapter: IShareView.Adapter

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) {

        // 设置背景颜色，不然截图的时候背景色是黑的
        setBackgroundColor(Color.parseColor("#FFFFFF"))
    }

    override fun setAdapter(adapter: IShareView.Adapter) {

        this.adapter = adapter

        if (childCount > 0) {
            removeAllViews()
        }

        // 添加封面
        for (index in 0 until imageCount) {

            addView(this.adapter.getCover(index))
        }

        // 添加介绍
        addView(this.adapter.getShareLayout())
    }

    override fun getAdapter(): IShareView.Adapter = adapter

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        // 宽度等于屏幕宽度
        actualWidth = widthSize
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

}



