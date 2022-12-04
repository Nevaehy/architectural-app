package `in`.test.pro.beta.data.network.rest.responseHandling.adapter

import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiError
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RestApiResponseAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<...errorHandling.NetworkResponse<<Foo>> or Call<...errorHandling.NetworkResponse<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != RestApiResponse::class.java) {
            return null
        }

        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as ...errorHandling.NetworkResponse<Foo> or ...errorHandling.NetworkResponse<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        val errorBodyConverter =
            retrofit.nextResponseBodyConverter<RestApiError>(null, RestApiError::class.java, annotations)

        return RestApiResponseAdapter<Any>(successBodyType, errorBodyConverter)
    }
}