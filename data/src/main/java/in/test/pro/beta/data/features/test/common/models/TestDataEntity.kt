package `in`.test.pro.beta.data.features.test.common.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TestDataEntity(
    @SerializedName("url")
    val url: String
)