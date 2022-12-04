package `in`.test.pro.beta.app.di.module.datamodule

import `in`.test.pro.beta.app.di.scopes.UserScope
import `in`.test.pro.beta.data.features.test.TestRepository
import `in`.test.pro.beta.data.features.test.common.TestRepositoryImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [TestDataModule::class]
)
interface DataModule {

    @UserScope
    @Binds
    fun provideTestRepository(impl: TestRepositoryImpl): TestRepository
}