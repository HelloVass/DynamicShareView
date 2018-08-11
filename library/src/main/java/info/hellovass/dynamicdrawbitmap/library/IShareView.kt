package info.hellovass.dynamicdrawbitmap.library

import io.reactivex.Observable

interface IShareView {

    fun getLocalPath(): Observable<String>
}