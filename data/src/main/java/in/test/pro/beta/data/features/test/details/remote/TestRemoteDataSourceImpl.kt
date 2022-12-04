package `in`.test.pro.beta.data.features.test.details.remote

import `in`.test.pro.beta.data.features.test.TestRestApi
import `in`.test.pro.beta.data.features.test.common.urlprovider.TestUrlProvider
import `in`.test.pro.beta.data.features.test.common.models.TestDataEntityResponse
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import `in`.test.pro.beta.data.network.rest.responseHandling.RestApiResponse
import javax.inject.Inject

class TestRemoteDataSourceImpl @Inject constructor(
    private val restApi: TestRestApi,
    private val urlProvider: TestUrlProvider,
) : TestRemoteDataSource {

    override suspend fun getTestData(
        requestData: TestRequestData
    ): RestApiResponse<TestDataEntityResponse> {
        return restApi.getTestData(
            url = urlProvider.getTestData().toString(),
            requestData = requestData
        )
    }
}