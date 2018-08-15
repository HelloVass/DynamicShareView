package info.hellovass.dynamicdrawbitmap.library.core

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.request.RequestOptions
import info.hellovass.dynamicdrawbitmap.library.R
import info.hellovass.dynamicdrawbitmap.library.ext.dp2px
import info.hellovass.dynamicdrawbitmap.library.imageloader.GlideLoader
import info.hellovass.dynamicdrawbitmap.library.imageloader.ImageLoader
import info.hellovass.dynamicdrawbitmap.library.qrcode.QRCodeEncoder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_userinfo.view.*

class InfoRender(private val context: Context) {

    private val infoRepo: InfoRepo by lazy {
        InfoRepo()
    }

    private val qrCodeEncoder: QRCodeEncoder by lazy {
        QRCodeEncoder()
    }

    private val imageLoader: ImageLoader by lazy {
        GlideLoader()
    }

    private val rlInfo by lazy {
        inflate()
    }

    fun render(): Observable<View> {
        return Observable.create<View> { emitter ->

            try {
                // 标题
                rlInfo.tvTitle.apply {
                    text = infoRepo.title()
                }

                // 头像
                rlInfo.ivAvatar.apply {

                    val bitmap = imageLoader.loadImage(
                            context,
                            infoRepo.avatarURL(),
                            RequestOptions.circleCropTransform()
                    )
                    setImageBitmap(bitmap)
                }

                // 昵称
                rlInfo.tvNickname.apply {
                    text = infoRepo.nickName()
                }

                // 二维码
                rlInfo.ivQRCode.apply {

                    val bitmap: Bitmap? = qrCodeEncoder.encodeAsBitmap(infoRepo.weibo(),
                            width = context.dp2px(128.0F),
                            height = context.dp2px(128.0F)
                    )
                    setImageBitmap(bitmap)
                }

                // 出处
                rlInfo.tvFrom.apply {
                    text = infoRepo.from()
                }
                emitter.onNext(rlInfo)
            } catch (e: Throwable) {
                emitter.onError(Throwable(""))
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun inflate(): View = LayoutInflater.from(context).inflate(R.layout.layout_userinfo, null)
}