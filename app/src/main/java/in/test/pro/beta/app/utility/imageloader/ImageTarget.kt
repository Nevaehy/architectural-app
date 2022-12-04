package `in`.test.pro.beta.app.utility.imageloader

interface ImageTarget {
    var errorDrawable: android.graphics.drawable.Drawable?

    var fallbackDrawable: android.graphics.drawable.Drawable?

    val imageView: android.widget.ImageView

    var placeholderDrawable: android.graphics.drawable.Drawable?
}