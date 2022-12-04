package `in`.test.pro.beta.app.base

import `in`.test.pro.beta.app.di.factory.ViewModelFactory
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.presentation.base.BaseViewModel
import `in`.test.pro.beta.app.utility.chrometab.CustomChromeTab
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.FragmentActivity
import javax.inject.Inject
import dagger.Lazy

abstract class BaseActivity : FragmentActivity, BaseView {

    private val delegate = BaseViewDelegate()

    @Inject
    internal lateinit var viewModelFactoryInternal: Lazy<ViewModelFactory>

    @Inject
    lateinit var customTabHelperInternal : Lazy<CustomChromeTab>

    val viewModelFactory : ViewModelFactory
        get() = viewModelFactoryInternal.get()

    private val customTabHelper : CustomChromeTab
        get() = customTabHelperInternal.get()

    constructor() : super()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewComponent().inject(this)
    }

    internal fun bindToLifecycle(viewModel: BaseViewModel) {
        delegate.bindViewModelToLifecycle(this, viewModel)
        delegate.listenStates(this, viewModel)
    }

    fun launchChromeTab(Url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabHelper.openCustomTab(this, customTabsIntent, Uri.parse(Url))
    }
}