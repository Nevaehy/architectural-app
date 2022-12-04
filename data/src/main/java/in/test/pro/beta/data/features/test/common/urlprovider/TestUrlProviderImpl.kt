package `in`.test.pro.beta.data.features.test.common.urlprovider

import java.net.URL
import javax.inject.Inject

class TestUrlProviderImpl @Inject constructor() : TestUrlProvider {

    override fun getTestData(): URL {
        return URL("$BASE_URL$TEST_DATA_URL")
    }

    companion object {
        // should be moved to flavors -> BuildConfig
        private const val BASE_URL = "https://api.openai.com/"

        private const val TEST_DATA_URL = "v1/images/generations"
    }
}