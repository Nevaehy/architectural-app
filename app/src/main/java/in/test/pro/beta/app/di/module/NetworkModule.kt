package `in`.test.pro.beta.app.di.module

import `in`.test.pro.beta.data.features.test.TestRestApi
import `in`.test.pro.beta.data.features.test.common.urlprovider.TestUrlProvider
import `in`.test.pro.beta.data.features.test.common.urlprovider.TestUrlProviderImpl
import `in`.test.pro.beta.data.network.rest.responseHandling.adapter.RestApiResponseAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RestApiResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideTestRestApi(retrofit: Retrofit): TestRestApi {
        return retrofit.create(TestRestApi::class.java)
    }

    @Provides
    fun provideTestUrlProvider(impl: TestUrlProviderImpl): TestUrlProvider {
        return impl
    }

    // should be moved to flavors -> BuildConfig
    private const val BASE_URL = "https://api.openai.com/"
}