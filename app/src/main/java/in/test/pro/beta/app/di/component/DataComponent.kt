package `in`.test.pro.beta.app.di.component

import `in`.test.pro.beta.app.di.module.datamodule.DataModule
import `in`.test.pro.beta.app.di.scopes.UserScope
import dagger.Subcomponent

@UserScope
@Subcomponent(modules = [DataModule::class])
interface DataComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): DataComponent
    }

    fun getViewComponentBuilder(): ViewComponent.Builder
}