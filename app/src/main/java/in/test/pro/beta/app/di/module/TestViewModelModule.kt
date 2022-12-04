package `in`.test.pro.beta.app.di.module

import `in`.test.pro.beta.app.di.annotation.ViewModelKey
import `in`.test.pro.beta.presentation.features.test.children.FirstChildViewModel
import `in`.test.pro.beta.presentation.features.test.children.SecondChildViewModel
import `in`.test.pro.beta.presentation.features.test.parent.TestParentViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TestViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestParentViewModel::class)
    abstract fun bindTestParentViewModel(viewModel: TestParentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FirstChildViewModel::class)
    abstract fun bindFirstChildViewModel(viewModel: FirstChildViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SecondChildViewModel::class)
    abstract fun bindSecondChildViewModel(viewModel: SecondChildViewModel): ViewModel
}