package `in`.test.pro.beta.app.common

import `in`.test.pro.beta.app.base.BaseBottomSheetDialogFragment
import `in`.test.pro.beta.app.base.BaseView
import `in`.test.pro.beta.app.base.BaseViewDelegate
import `in`.test.pro.beta.app.di.factory.ViewModelFactory
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.presentation.base.BaseViewModel
import android.content.Context
import android.content.res.Resources
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

abstract class BottomSheetDialogFragment : BaseBottomSheetDialogFragment(), BaseView {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val delegate = BaseViewDelegate()

    internal fun bindToLifecycle(viewModel: BaseViewModel) {
        delegate.bindViewModelToLifecycle(this, viewModel)
        delegate.listenStates(this, viewModel)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        tag?.let {
            // ignore this show call if the fragment is already added to FragmentManager EXCEPT the cases of adding via nav component
            if (it.startsWith(NAV_COMPONENT_FRAGMENT_TAG_PREFIX).not() && manager.findFragmentByTag(it) != null) {
                return
            }
        }

        super.show(manager, tag)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }

    fun invalidateDialogHeight() {
        val bottomSheet = requireDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        BottomSheetBehavior.from(bottomSheet).apply {
            skipCollapsed = true
            peekHeight = Resources.getSystem().displayMetrics.heightPixels
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private companion object {

        private const val NAV_COMPONENT_FRAGMENT_TAG_PREFIX = "androidx-nav-fragment"
    }
}