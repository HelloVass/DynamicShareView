package info.hellovass.dynamicdrawbitmap.library

import android.view.View
import io.reactivex.Observable

interface RenderDelegate {

    fun getLayoutResId(): Int

    fun render(): Observable<View>
}