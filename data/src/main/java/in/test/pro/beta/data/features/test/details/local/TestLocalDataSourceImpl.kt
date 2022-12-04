package `in`.test.pro.beta.data.features.test.details.local

import `in`.test.pro.beta.data.features.test.common.models.TestDataEntity
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import javax.inject.Inject

class TestLocalDataSourceImpl @Inject constructor() : TestLocalDataSource {

    private val idToTestDataMap: MutableMap<TestRequestData, List<TestDataEntity>?> = hashMapOf()

    override suspend fun getTestData(requestData: TestRequestData): List<TestDataEntity>? {
        return idToTestDataMap[requestData]
    }

    override suspend fun saveTestData(requestData: TestRequestData, dataTestData: List<TestDataEntity>) {
        idToTestDataMap[requestData] = dataTestData
    }

    override suspend fun clearAllCache() {
        idToTestDataMap.clear()
    }
}