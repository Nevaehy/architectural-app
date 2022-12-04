package `in`.test.pro.beta.data.network.rest.responseHandling

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RestApiError(
    @SerializedName("error")
    val error: Error?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("code")
    val code: Int?
)

@Keep
data class Error(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,

    //Reason is used in balance APIs
    @SerializedName("reason")
    val reason: String?
)