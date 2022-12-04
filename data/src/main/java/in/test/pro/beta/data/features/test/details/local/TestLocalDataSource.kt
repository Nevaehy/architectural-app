package `in`.test.pro.beta.data.features.test.details.local

import `in`.test.pro.beta.data.features.test.common.models.TestDataEntity
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData

interface TestLocalDataSource {

    suspend fun getTestData(requestData: TestRequestData): List<TestDataEntity>?
    suspend fun saveTestData(requestData: TestRequestData, dataTestData: List<TestDataEntity>)
    suspend fun clearAllCache()
}