package `in`.test.pro.beta.app.di.module.datamodule

import `in`.test.pro.beta.app.di.scopes.UserScope
import `in`.test.pro.beta.data.features.test.details.local.TestLocalDataSource
import `in`.test.pro.beta.data.features.test.details.local.TestLocalDataSourceImpl
import `in`.test.pro.beta.data.features.test.details.remote.TestRemoteDataSource
import `in`.test.pro.beta.data.features.test.details.remote.TestRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface TestDataModule {

    @UserScope
    @Binds
    fun provideTestRemoteDataSource(impl: TestRemoteDataSourceImpl): TestRemoteDataSource

    @UserScope
    @Binds
    fun provideTestLocalDataSource(impl: TestLocalDataSourceImpl): TestLocalDataSource
}