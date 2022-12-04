package `in`.test.pro.beta.app.utility.imageloader

import android.graphics.drawable.Drawable as ISDrawable
import com.bumptech.glide.load.model.GlideUrl as ISGlideUrl

sealed class ImageSource {
    data class Url(val url: String?) : ImageSource()
    data class Drawable(val drawable: ISDrawable?) : ImageSource()
    data class GlideUrl(val glideUrl: ISGlideUrl?) : ImageSource()
}