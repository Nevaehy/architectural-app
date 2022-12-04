package `in`.test.pro.beta.app.utility.imageloader

import `in`.test.pro.beta.app.utility.imageloader.ImageScaleType.CENTER_CROP
import `in`.test.pro.beta.app.utility.imageloader.ImageScaleType.FIT_CENTER
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageTarget.loadFromUrl(
    parent: ImageParent,
    url: String?,
    options: ImageOptions = ImageOptions(),
) {
    loadFromSource(parent, ImageSource.Url(url), options)
}

fun ImageTarget.loadFromSource(
    parent: ImageParent,
    source: ImageSource,
    options: ImageOptions = ImageOptions()
) {
    load(parent, source)
        .fallback(fallbackDrawable)
        .placeholder(placeholderDrawable)
        .error(errorDrawable)
        .apply {
            options.cornersRadiusPx?.let {
                applyRoundedCorners(options.cornersRadiusPx, options.scaleType)
            } ?: applyScaleType(options.noTransform, options.scaleType)
        }
        .addListeners(options.successListener, options.errorListener)
        .into(imageView)
}

// the order is important - if we have Rounded corners, then we need to Crop the image via transform only, then apply Rounded corners
private fun <T : Drawable> GlideRequest<T>.applyRoundedCorners(radiusPx: Int?, scaleType: ImageScaleType): GlideRequest<T> {
    return radiusPx?.let {
        transform(
            when (scaleType) {
                CENTER_CROP -> CenterCrop()
                FIT_CENTER -> FitCenter()
            },
            RoundedCorners(it)
        )
    } ?: this
}

private fun <T : Drawable> GlideRequest<T>.applyScaleType(
    noTransform: Boolean,
    scaleType: ImageScaleType
): GlideRequest<T> {
    if (noTransform) {
        return dontTransform()
    }
    return when (scaleType) {
        ImageScaleType.CENTER_CROP -> centerCrop()
        ImageScaleType.FIT_CENTER -> fitCenter()
    }
}

private fun <T : Drawable> GlideRequest<T>.addListeners(
    successListener: (() -> Unit)? = null,
    errorListener: ((exception: Exception?) -> Unit)? = null
): GlideRequest<T> {
    return addListener(object : RequestListener<T> {
        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            successListener?.invoke()
            return false
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            errorListener?.invoke(e)
            return false
        }
    })
}

private fun load(
    parent: ImageParent,
    source: ImageSource
): GlideRequest<Drawable> {
    val request = getGlideRequests(parent)
    return when (source) {
        is ImageSource.Url -> request.load(source.url)
        is ImageSource.Drawable -> request.load(source.drawable)
        is ImageSource.GlideUrl -> request.load(source.glideUrl)
    }
}

private fun getGlideRequests(parent: ImageParent) = when (parent) {
    is ImageParent.ParentActivity -> GlideApp.with(parent.activity)
    is ImageParent.ParentFragment -> GlideApp.with(parent.fragment)
    is ImageParent.ParentView -> GlideApp.with(parent.view)
}