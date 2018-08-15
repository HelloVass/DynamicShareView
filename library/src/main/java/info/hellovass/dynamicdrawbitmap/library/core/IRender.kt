package info.hellovass.dynamicdrawbitmap.library.core

import android.view.View
import io.reactivex.Observable

interface IRender {

    fun getLayoutResId(): Int

    fun render(): Observable<View>
}