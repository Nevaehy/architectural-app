package `in`.test.pro.beta.app.base

import `in`.test.pro.beta.app.di.factory.ViewModelFactory
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.presentation.base.BaseViewModel
import android.content.Context
import androidx.fragment.app.DialogFragment
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(), BaseView {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val delegate = BaseViewDelegate()

    internal fun bindToLifecycle(viewModel: BaseViewModel) {
        delegate.bindViewModelToLifecycle(this, viewModel)
        delegate.listenStates(this, viewModel)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }
}