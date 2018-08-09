package info.hellovass.dynamicdrawbitmap.library

import android.view.View

interface IShareView {

    fun setAdapter(adapter: Adapter)

    fun getAdapter(): Adapter

    interface Adapter {

        fun getCount(): Int

        fun getCover(index: Int): View

        fun getShareLayout(): View
    }
}