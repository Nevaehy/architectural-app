package `in`.test.pro.beta.app.base

import `in`.test.pro.beta.presentation.base.BaseViewModel
import `in`.test.pro.beta.presentation.base.State.Content
import `in`.test.pro.beta.presentation.base.State.Error
import `in`.test.pro.beta.presentation.base.State.Loading
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner

interface BaseView : LifecycleOwner, ViewModelStoreOwner {

    fun showLoader() = Unit

    fun showLoader(message: String) = Unit

    fun showError() = Unit

    fun showContent() = Unit
}

class BaseViewDelegate {

    fun listenStates(baseView: BaseView, viewModel: BaseViewModel) {
        viewModel.stateLiveData.observe(baseView) {
            when (it) {
                is Loading -> baseView.showLoader()
                is Content -> baseView.showContent()
                is Error -> baseView.showError()
            }
        }
    }

    fun bindViewModelToLifecycle(baseView: BaseView, viewModel: BaseViewModel) {
        baseView.lifecycle.addObserver(viewModel)
    }
}