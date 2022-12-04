package `in`.test.pro.beta.data.base

import `in`.test.pro.beta.data.network.rest.base.response.BaseRestResponse
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiResponse
import `in`.test.pro.beta.data.network.socket.client.SocketResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseRepository {

    val coroutineIoScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val appScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun <T : Any> handleRestResponse(response: RestApiResponse<T>): T {
        return when (response) {
            is RestApiResponse.ApiError -> {
                val errorMessage = response.body.error?.reason
                    ?: response.body.error?.message
                    ?: response.body.message
                    ?: ""

                val errorCode = response.body.error?.code ?: response.code
                throw RestApiException(errorMessage, errorCode)
            }
            is RestApiResponse.NetworkError -> {
                throw NetworkErrorException()
            }
            is RestApiResponse.UnknownError -> {
                throw RestApiException("oops something went wrong [${response.code}]", response.code)
            }
            else -> {
                val baseResponse = ((response as RestApiResponse.Success).body as BaseRestResponse<*>)
                when {
                    baseResponse.isSuccess || baseResponse.data != null -> {
                        response.body
                    }
                    baseResponse.isSuccess.not() -> {
                        val errorCode: Int = baseResponse.error?.error?.code ?: baseResponse.error?.code ?: -1
                        var errorMessage: String = baseResponse.error?.error?.message
                            ?: baseResponse.error?.message
                            ?: UNKNOWN_API_ERROR

                        errorMessage += " [$errorCode]"
                        throw RestApiException(errorMessage, errorCode)
                    }
                    else -> {
                        throw RuntimeException(UNKNOWN_API_ERROR)
                    }
                }
            }
        }
    }

    fun <T : Any> handleAccountOpeningRestResponse(response: RestApiResponse<T>): T {
        return when (response) {
            is RestApiResponse.ApiError -> {
                val errorMessage = response.body.error?.reason
                    ?: response.body.error?.message
                    ?: response.body.message.orEmpty()

                val errorCode = response.body.error?.code ?: response.code
                throw RestApiException(errorMessage, errorCode)
            }
            is RestApiResponse.NetworkError -> {
                throw NetworkErrorException()
            }
            is RestApiResponse.UnknownError -> {
                throw RestApiException("oops something went wrong [${response.code}]", response.code)
            }
            else -> {
                val baseResponse = ((response as RestApiResponse.Success).body as BaseRestResponse<*>)
                when {
                    baseResponse.isSuccess || baseResponse.data != null -> {
                        response.body
                    }
                    baseResponse.isSuccess.not() -> {
                        val errorCode: Int = baseResponse.error?.error?.code ?: baseResponse.error?.code ?: -1

                        var errorMessage: String = baseResponse.error?.error?.message
                            ?: baseResponse.error?.message
                            ?: UNKNOWN_API_ERROR

                        errorMessage += " [$errorCode]"
                        throw RestApiException(errorMessage, errorCode)
                    }
                    else -> {
                        throw RuntimeException(UNKNOWN_API_ERROR)
                    }
                }
            }
        }
    }

    private fun <T : Any> handleSocketCallResponse(response: SocketResponse<T>): T {
        return if (response.error != null) {
            throw SocketApiException(
                response.error.message
                    ?: COMMON_ERROR_MESSAGE, response.error.code ?: 0
            )
        } else {
            response.data!!
        }
    }

    /*
        This will work as fire and forget function as we are not waiting for it to complete or its response
     */
    @OptIn(DelicateCoroutinesApi::class)
    protected suspend fun <T> withGlobalScope(block: suspend CoroutineScope.() -> T) {
        GlobalScope.launch(Dispatchers.IO) {
            block()
        }
    }

    companion object {
        const val UNKNOWN_API_ERROR = "Unknown API Error"
        const val COMMON_ERROR_MESSAGE = "Something went wrong"
    }
}