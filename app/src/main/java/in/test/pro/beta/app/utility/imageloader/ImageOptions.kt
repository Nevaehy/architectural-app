package `in`.test.pro.beta.app.utility.imageloader

data class ImageOptions(
    val scaleType: ImageScaleType = ImageScaleType.CENTER_CROP,
    val applyCircleCrop: Boolean = false,
    val cornersRadiusPx: Int? = 10,
    val crossFadeDurationMs: Int? = 300, // Glide's default cross fade duration is 300
    val noTransform: Boolean = false,
    val successListener: (() -> Unit)? = null,
    val errorListener: ((ex: Exception?) -> Unit)? = null
)