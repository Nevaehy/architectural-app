package `in`.test.pro.beta.data.features.test.common.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TestRequestData(
    @SerializedName("prompt")
    val prompt: String,
    @SerializedName("n")
    val n: Int,
    @SerializedName("size")
    val size: String
)
