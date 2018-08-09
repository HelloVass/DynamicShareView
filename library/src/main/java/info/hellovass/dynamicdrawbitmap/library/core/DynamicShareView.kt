package info.hellovass.dynamicdrawbitmap.library.core

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import info.hellovass.dynamicdrawbitmap.library.rule.BottomRule
import info.hellovass.dynamicdrawbitmap.library.rule.Rule
import info.hellovass.dynamicdrawbitmap.library.rule.Rule1Impl
import info.hellovass.dynamicdrawbitmap.library.rule.Rule2Impl
import info.hellovass.dynamicdrawbitmap.library.rule.Rule3Impl
import info.hellovass.dynamicdrawbitmap.library.rule.Rule4Impl
import java.util.ArrayList

/**
 * Created by hello on 2017/12/8.
 */

class DynamicShareView : ViewGroup {

    private val mSpacing = dp2px(1.0f) // 默认间隔1dp

    private var mAdapter: Adapter? = null

    private val mImages = ArrayList<View>(MAX_COUNT)

    private var mBottomLayout: View? = null

    private var mActualWidth: Int = 0

    private var mActualHeight: Int = 0

    private var mRule1: Rule? = null

    private var mRule2: Rule? = null

    private var mRule3: Rule? = null

    private var mRule4: Rule? = null

    private var mBottomRule: Rule? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {

        setBackgroundColor(Color.parseColor("#FFFFFF")) // 设置背景颜色，不然截图的时候背景色是黑的

        mRule1 = Rule1Impl(context)
        mRule2 = Rule2Impl(context)
        mRule3 = Rule3Impl(context)
        mRule4 = Rule4Impl(context)
        mBottomRule = BottomRule(context)
    }

    fun setAdapter(adapter: Adapter) {

        mAdapter = adapter

        removeAllViews() // 移除所有子View

        // 添加图片
        val imageCount = Math.min(mAdapter!!.coverCount, MAX_COUNT)
        for (index in 0 until imageCount) {

            val image = mAdapter!!.getImage(this, index)
            addView(image)
            mImages.add(image)
        }

        // 添加底部
        val bottomLayout = mAdapter!!.getBottom(this)
        mBottomLayout = bottomLayout
        addView(bottomLayout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        mActualWidth = widthSize // 宽度等于屏幕宽度

        when (mImages.size) {

            1 -> mRule1!!.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages[0])

            2 -> mRule2!!.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages[0],
                    mImages[1])

            3 -> mRule3!!.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages[0],
                    mImages[1], mImages[2])

            4 -> mRule4!!.measureChildren(mActualWidth, mActualHeight, mSpacing, mImages[0],
                    mImages[1], mImages[2], mImages[3])
        }

        // 测量底部高度
        mBottomRule!!.measureChildren(mActualWidth, mActualHeight, mSpacing, mBottomLayout)

        // 获得实际高度
        mActualHeight = mActualWidth + mBottomLayout!!.measuredHeight

        // 设置 ViewGroup 尺寸
        setMeasuredDimension(mActualWidth,
                if (heightMode == View.MeasureSpec.EXACTLY) heightSize else mActualHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        if (mImages == null || mImages.isEmpty()) return

        when (mImages.size) {

            1 -> mRule1!!.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages[0])

            2 -> mRule2!!.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages[0],
                    mImages[1])

            3 -> mRule3!!.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages[0], mImages[1],
                    mImages[2])

            4 -> mRule4!!.layoutChildren(mActualWidth, mActualHeight, mSpacing, mImages[0], mImages[1],
                    mImages[2], mImages[3])
        }

        mBottomRule!!.layoutChildren(mActualWidth, mActualHeight, mSpacing, mBottomLayout)
    }

    private fun dp2px(dp: Float): Int {

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.displayMetrics).toInt()
    }

    companion object {

        private val MAX_COUNT = 4
    }
}
