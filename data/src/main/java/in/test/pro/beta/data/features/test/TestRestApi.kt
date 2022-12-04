package `in`.test.pro.beta.data.features.test

import `in`.test.pro.beta.data.features.test.common.models.TestDataEntityResponse
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import `in`.test.pro.beta.data.network.OkHttpApiClientConstants.APPLICATION_JSON
import `in`.test.pro.beta.data.network.OkHttpApiClientConstants.AUTHORIZATION
import `in`.test.pro.beta.data.network.OkHttpApiClientConstants.AUTH_TOKEN
import `in`.test.pro.beta.data.network.OkHttpApiClientConstants.BEARER
import `in`.test.pro.beta.data.network.OkHttpApiClientConstants.CONTENT_TYPE
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface TestRestApi {

    @POST
    @Headers(
        "$CONTENT_TYPE: $APPLICATION_JSON",
        "$AUTHORIZATION: $BEARER$AUTH_TOKEN",
    )
    suspend fun getTestData(
        @Url url: String,
        @Body requestData: TestRequestData,
    ): RestApiResponse<TestDataEntityResponse>
}