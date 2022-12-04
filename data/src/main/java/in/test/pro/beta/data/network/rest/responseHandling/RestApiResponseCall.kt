package `in`.test.pro.beta.data.network.rest.responseHandling

import java.io.IOException
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class RestApiResponseCall<S : Any>(
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, RestApiError>
) : Call<RestApiResponse<S>> {

    override fun enqueue(callback: Callback<RestApiResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@RestApiResponseCall,
                            Response.success(RestApiResponse.Success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@RestApiResponseCall,
                            Response.success(RestApiResponse.UnknownError(null, code))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null || error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@RestApiResponseCall,
                            Response.success(RestApiResponse.ApiError(errorBody, code))
                        )
                    } else {
                        callback.onResponse(
                            this@RestApiResponseCall,
                            Response.success(RestApiResponse.UnknownError(null, code))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> RestApiResponse.NetworkError(throwable)
                    else -> RestApiResponse.UnknownError(throwable, 0)
                }
                callback.onResponse(this@RestApiResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun timeout(): Timeout {
        return Timeout()
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = RestApiResponseCall<S>(delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<RestApiResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    companion object {
        const val UNAUTHORIZED = 401
    }
}