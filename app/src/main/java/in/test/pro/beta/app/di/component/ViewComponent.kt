package `in`.test.pro.beta.app.di.component

import `in`.test.pro.beta.app.base.BaseActivity
import `in`.test.pro.beta.app.base.BaseBottomSheetDialogFragment
import `in`.test.pro.beta.app.base.BaseDialogFragment
import `in`.test.pro.beta.app.base.BaseFragment
import `in`.test.pro.beta.app.common.BottomSheetDialogFragment
import `in`.test.pro.beta.app.di.module.TestViewModelModule
import `in`.test.pro.beta.app.features.test.parent.TestParentActivity
import `in`.test.pro.beta.app.features.test.second.TestSecondChildFragment
import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [TestViewModelModule::class])
interface ViewComponent {

    fun inject(injectee: BaseActivity)
    fun inject(injectee: BaseBottomSheetDialogFragment)
    fun inject(injectee: BottomSheetDialogFragment)
    fun inject(injectee: BaseFragment)
    fun inject(injectee: TestParentActivity)
    fun inject(injectee: TestSecondChildFragment)
    fun inject(injectee: BaseDialogFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun context(activityContext: Context): Builder
        fun build(): ViewComponent
    }
}