package `in`.test.pro.beta.domain.features.test

import `in`.test.pro.beta.data.features.test.TestRepository
import `in`.test.pro.beta.domain.base.BaseInteractor
import `in`.test.pro.beta.domain.base.NetworkErrorException
import `in`.test.pro.beta.domain.base.Outcome
import `in`.test.pro.beta.domain.base.RestApiException
import `in`.test.pro.beta.domain.features.test.models.TestData
import `in`.test.pro.beta.domain.features.test.mappers.TestEntityToDataMapper
import `in`.test.pro.beta.domain.features.test.mappers.TestRequestDomainToDataMapper
import `in`.test.pro.beta.domain.features.test.models.DTestRequestData
import javax.inject.Inject

class TestInteractor @Inject constructor(
    private val testRepository: TestRepository,
    private val testMapper: TestEntityToDataMapper,
    private val requestMapper: TestRequestDomainToDataMapper
) : BaseInteractor() {

    suspend fun getTestData(
        requestData: DTestRequestData
    ): Outcome<TestData, GetTestError> {
        return withIOContext {
            return@withIOContext try {
                val data = testRepository.getTestData(requestMapper.map(requestData))
                Outcome.Success(testMapper.mapList(data).first())
            } catch (e: RestApiException) {
                Outcome.Error(GetTestError.BackendError(e.errorCode, e.message))
            } catch (e: NetworkErrorException) {
                Outcome.NetworkConnection(e)
            }
        }
    }

    suspend fun clearAllCache() {
        withIOContext {
            testRepository.clearAllCache()
        }
    }
}