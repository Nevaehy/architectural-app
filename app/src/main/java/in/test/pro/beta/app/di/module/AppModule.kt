package `in`.test.pro.beta.app.di.module

import `in`.test.pro.beta.app.MyApplication
import `in`.test.pro.beta.app.di.AppContext
import `in`.test.pro.beta.domain.di.ModuleContext
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @AppContext
    fun providesApplicationContext(application: MyApplication): Context = application

    @Provides
    @Singleton
    @ModuleContext
    fun providesContext(application: MyApplication): Context = application

}