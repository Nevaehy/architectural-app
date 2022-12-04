package `in`.test.pro.beta.domain.features.test.models

import androidx.annotation.Keep

@Keep
data class DTestRequestData(
    val prompt: String,
    val n: Int,
    val size: String
)
