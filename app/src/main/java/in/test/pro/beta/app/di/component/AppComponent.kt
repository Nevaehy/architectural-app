package `in`.test.pro.beta.app.di.component

import `in`.test.pro.beta.app.MyApplication
import `in`.test.pro.beta.app.di.module.AppModule
import `in`.test.pro.beta.app.di.module.NetworkModule
import `in`.test.pro.beta.app.di.module.UtilityModule
import `in`.test.pro.beta.app.di.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        UtilityModule::class,
        NetworkModule::class,
        ViewModelFactoryModule::class
    ]
)
@Singleton
interface AppComponent {

    fun getDataComponentBuilder(): DataComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: MyApplication): Builder
        fun build(): AppComponent
    }

    fun inject(application: MyApplication)
}