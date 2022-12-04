package `in`.test.pro.beta.app.di.module

import `in`.test.pro.beta.app.utility.chrometab.CustomChromeTab
import `in`.test.pro.beta.app.utility.chrometab.CustomChromeTabImpl
import `in`.test.pro.beta.presentation.utils.resourcemanager.ResourceManager
import `in`.test.pro.beta.presentation.utils.resourcemanager.ResourceManagerImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UtilityModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideResourceManager(resourceManager: ResourceManagerImpl): ResourceManager {
        return resourceManager
    }

    @Provides
    @JvmStatic
    fun provideCustomChromeTabActivityHelper(
        customTabHelperImpl: CustomChromeTabImpl
    ): CustomChromeTab {
        return customTabHelperImpl
    }

    @Provides
    @Singleton
    @JvmStatic
    fun gson(): Gson {
        return GsonBuilder().create()
    }
}