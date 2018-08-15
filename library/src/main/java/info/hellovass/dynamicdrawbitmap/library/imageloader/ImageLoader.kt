package info.hellovass.dynamicdrawbitmap.library.imageloader

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.request.RequestOptions

interface ImageLoader {

    fun loadImage(
            context: Context,
            imageUrl: String,
            options: RequestOptions = RequestOptions.centerCropTransform()
    ): Bitmap
}