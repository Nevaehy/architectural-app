package `in`.test.pro.beta.app.base

import `in`.test.pro.beta.app.di.factory.ViewModelFactory
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.presentation.base.BaseViewModel
import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import javax.inject.Inject

abstract class BaseFragment : Fragment, BaseView {
    constructor() : super()
    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val delegate = BaseViewDelegate()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }

    internal fun bindToLifecycle(viewModel: BaseViewModel) {
        delegate.bindViewModelToLifecycle(this, viewModel)
        delegate.listenStates(this, viewModel)
    }

    fun launchChromeTab(url: String) {
        (activity as? BaseActivity)?.launchChromeTab(url)
    }
}