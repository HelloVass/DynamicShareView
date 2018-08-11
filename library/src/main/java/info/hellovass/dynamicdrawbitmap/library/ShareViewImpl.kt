package info.hellovass.dynamicdrawbitmap.library

import android.content.Context
import android.graphics.Color
import android.view.View
import info.hellovass.dynamicdrawbitmap.library.ext.ViewUtil
import info.hellovass.dynamicdrawbitmap.library.ext.createBitmap
import info.hellovass.dynamicdrawbitmap.library.ext.getScreenWidth
import info.hellovass.dynamicdrawbitmap.library.ext.saveToLocal
import io.reactivex.Observable
import io.reactivex.ObservableSource

class ShareViewImpl(private val context: Context, private val renderDelegate: RenderDelegate) : IShareView {

    companion object {
        const val TEMP_FILE_PATH = "dynamic_share_image"
    }

    override fun getLocalPath(): Observable<String> {

        return renderDelegate.render()
                .flatMap { it -> saveToLocal(it) }
    }

    private fun saveToLocal(view: View): ObservableSource<String> {

        return Observable.create { emitter ->
            try {
                // step1，测量
                ViewUtil.measure(view, context.getScreenWidth())
                view.layout(0, 0, view.measuredWidth, view.measuredHeight)

                // step2 生成 bitmap
                val bitmap = view.createBitmap(Color.parseColor("#FFFFFF"))
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


