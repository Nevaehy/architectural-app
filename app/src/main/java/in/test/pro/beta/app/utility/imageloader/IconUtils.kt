package `in`.test.pro.beta.app.utility.imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class IconUtils(val context: Context) : ImageTarget {

    private var imageIcon: AppCompatImageView? = null

    fun loadUrlOnImageView(
        imageView: AppCompatImageView,
        url: String,
        options: ImageOptions = ImageOptions()
    ) {
        imageIcon = imageView
        this.loadFromUrl(
            parent = ImageParent.ParentView(imageView),
            url = url,
            options = options.copy(scaleType = ImageScaleType.FIT_CENTER)
        )
    }

    override val imageView: ImageView
        get() = imageIcon!!
    override var placeholderDrawable: Drawable? = null
    override var errorDrawable: Drawable? = null
    override var fallbackDrawable: Drawable? = null
}