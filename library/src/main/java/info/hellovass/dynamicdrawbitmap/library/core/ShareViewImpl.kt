package info.hellovass.dynamicdrawbitmap.library.core

import android.content.Context
import android.graphics.Color
import android.view.View
import info.hellovass.dynamicdrawbitmap.library.ext.createBitmap
import info.hellovass.dynamicdrawbitmap.library.ext.measureMually
import info.hellovass.dynamicdrawbitmap.library.ext.saveToLocal
import info.hellovass.dynamicdrawbitmap.library.ext.screenWidth
import io.reactivex.Observable
import io.reactivex.ObservableSource

class ShareViewImpl(private val context: Context, private val IRender: IRender) : IShareView {

    companion object {
        const val TEMP_FILE_PATH = "dynamic_share_image"
    }

    override fun getLocalPath(): Observable<String> {

        return IRender.render()
                .flatMap { it -> saveToLocal(it) }
    }

    private fun saveToLocal(view: View): ObservableSource<String> {

        return Observable.create { emitter ->
            try {
                // step1，测量
                view.measureMually(width = context.screenWidth())
                view.layout(0, 0, view.measuredWidth, view.measuredHeight)

                // step2 生成 bitmap
                val bitmap = view.createBitmap(Color.WHITE)
                // 创建临时文件
                val tempFile = createTempFile(TEMP_FILE_PATH, ".jpg", context.cacheDir)
                // step3 将 bitmap 保存到本地
                bitmap.saveToLocal(tempFile)
                // step4，成功
                emitter.onNext(tempFile.absolutePath)
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
    }
}


