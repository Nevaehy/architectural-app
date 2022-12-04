package `in`.test.pro.beta.domain.features.test.mappers

import `in`.test.pro.beta.data.base.Mapper
import `in`.test.pro.beta.data.features.test.common.models.TestRequestData
import `in`.test.pro.beta.domain.features.test.models.DTestRequestData
import javax.inject.Inject

class TestRequestDomainToDataMapper @Inject constructor() : Mapper<DTestRequestData, TestRequestData>() {
    override fun map(input: DTestRequestData): TestRequestData {
        return TestRequestData(
            prompt = input.prompt,
            n = input.n,
            size = input.size
        )
    }
}