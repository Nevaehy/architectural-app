package `in`.test.pro.beta.domain.features.test.mappers

import `in`.test.pro.beta.data.base.Mapper
import `in`.test.pro.beta.data.features.test.common.models.TestDataEntity
import `in`.test.pro.beta.domain.features.test.models.TestData
import javax.inject.Inject

class TestEntityToDataMapper @Inject constructor() : Mapper<TestDataEntity, TestData>() {

    override fun map(input: TestDataEntity): TestData {
        return TestData(
            url = input.url
        )
    }

}