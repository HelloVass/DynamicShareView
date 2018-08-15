package info.hellovass.dynamicdrawbitmap.library.core

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import info.hellovass.dynamicdrawbitmap.library.R
import io.reactivex.Observable
import io.reactivex.functions.Function3

/**
 * 默认的渲染器
 */
class DefaultIRender(private val context: Context) : IRender {

    private val container = inflate() as LinearLayout

    private val coversRedner: CoversRedner = CoversRedner(context)

    private val spaceRender: SpaceRender = SpaceRender(context)

    private val infoRender: InfoRender = InfoRender(context)

    override fun getLayoutResId(): Int = R.layout.layout_container

    override fun render(): Observable<View> {

        val o1 = coversRedner.render()
        val o2 = spaceRender.render()
        val o3 = infoRender.render()

        return Observable.zip(o1, o2, o3, Function3<View, View, View, View> { t1, t2, t3 ->
            container.addView(t1)
            container.addView(t2)
            container.addView(t3)
            container
        })
    }

    @SuppressLint("InflateParams")
    private fun inflate(): View = LayoutInflater.from(context).inflate(R.layout.layout_container, null)
}


