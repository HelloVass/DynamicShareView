package info.hellovass.dynamicdrawbitmap.library.core

import io.reactivex.Observable

interface IShareView {

    fun getLocalPath(): Observable<String>
}