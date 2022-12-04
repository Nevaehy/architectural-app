package `in`.test.pro.beta.data.features.test.details.remote

import `in`.test.pro.beta.data.features.test.common.models.TestDataEntityResponse
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import `in`.test.pro.beta.data.network.rest.base.request.BaseRestRequest
import `in`.test.pro.beta.data.network.rest.base.response.BaseRestResponse
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiResponse
import okhttp3.ResponseBody
import org.json.JSONObject

interface TestRemoteDataSource {

    suspend fun getTestData(requestData: TestRequestData): RestApiResponse<TestDataEntityResponse>

}