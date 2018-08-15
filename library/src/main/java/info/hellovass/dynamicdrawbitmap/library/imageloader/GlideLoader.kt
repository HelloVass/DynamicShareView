package info.hellovass.dynamicdrawbitmap.library.imageloader

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class GlideLoader : ImageLoader {

    override
    fun loadImage(context: Context, imageUrl: String, options: RequestOptions): Bitmap {

        return Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .submit()
                .get()
    }
}