package info.hellovass.dynamicdrawbitmap.library.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import info.hellovass.dynamicdrawbitmap.library.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SpaceRender(private val context: Context) {

    private val space by lazy {
        inflate()
    }

    fun render(): Observable<View> {
        return Observable.create<View> { emitter ->

            try {
                emitter.onNext(space)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun inflate(): View = LayoutInflater.from(context).inflate(R.layout.layout_space, null)
}