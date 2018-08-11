package info.hellovass.dynamicdrawbitmap.library.ext

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

internal fun Bitmap.saveToLocal(tempFile: File) {

    val fileOutputStream = FileOutputStream(tempFile)
    this.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()
}