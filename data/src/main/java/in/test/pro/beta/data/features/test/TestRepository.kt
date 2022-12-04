package `in`.test.pro.beta.data.features.test

import `in`.test.pro.beta.data.features.test.common.models.TestDataEntity
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData

interface TestRepository {

    suspend fun getTestData(requestData: TestRequestData): List<TestDataEntity>

    suspend fun clearAllCache()
}