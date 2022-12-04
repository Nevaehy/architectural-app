package `in`.test.pro.beta.data.features.test.common

import `in`.test.pro.beta.data.base.BaseRepository
import `in`.test.pro.beta.data.features.test.TestRepository
import `in`.test.pro.beta.data.features.test.common.models.TestDataEntity
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import `in`.test.pro.beta.data.features.test.details.local.TestLocalDataSource
import `in`.test.pro.beta.data.features.test.details.remote.TestRemoteDataSource
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val remoteDataSource: TestRemoteDataSource,
    private val localDataSource: TestLocalDataSource
) : TestRepository, BaseRepository() {

    override suspend fun getTestData(requestData: TestRequestData): List<TestDataEntity> {
        localDataSource.getTestData(requestData)?.let {
            return it
        }

        val testEntity = handleRestResponse(remoteDataSource.getTestData(requestData))

        return testEntity.data!!.also {
            localDataSource.saveTestData(requestData, it)
        }
    }

    override suspend fun clearAllCache() {
        localDataSource.clearAllCache()
    }
}