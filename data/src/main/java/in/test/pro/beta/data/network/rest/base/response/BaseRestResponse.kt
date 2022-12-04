package `in`.test.pro.beta.data.network.rest.base.response

import `in`.test.pro.beta.data.base.ErrorFields
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiError
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
open class BaseRestResponse<T> {
    @SerializedName("data")
    var data: T? = null

    @SerializedName("error")
    var error: RestApiError? = null

    @SerializedName("success")
    var isSuccess: Boolean = false

    @SerializedName("errorFields")
    var errorFields: ErrorFields? = null
}