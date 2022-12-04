package `in`.test.pro.beta.app.features.test.parent

import `in`.test.pro.beta.R
import `in`.test.pro.beta.app.base.BaseFragment
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.app.features.test.parent.adapter.TestParentPagerAdapter
import `in`.test.pro.beta.databinding.FragmentTestParentBinding
import `in`.test.pro.beta.presentation.base.State
import `in`.test.pro.beta.presentation.features.test.parent.TestParentNavigation
import `in`.test.pro.beta.presentation.features.test.parent.TestParentViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator

class TestParentFragment : BaseFragment() {

    private val sharedViewModel by activityViewModels<TestParentViewModel> { viewModelFactory }
    
    private var _binding: FragmentTestParentBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var pagerAdapter: TestParentPagerAdapter? = null

    private val tabsTitles by lazy {
        arrayOf(
            getString(R.string.first),
            getString(R.string.second)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentTestParentBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initListeners()
        initObservers()
    }

    private fun initViewPager() {
        pagerAdapter = TestParentPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsTitles[position]
        }.attach()
    }

    private fun initListeners() {
        binding.btnTryAgain.setOnClickListener { sharedViewModel.retry() }
    }

    private fun initObservers() {
        sharedViewModel.navigation().observe(viewLifecycleOwner) {
            when (it) {
                is TestParentNavigation.OpenLink -> {
                    // open webview or chrome tab
                }
            }
        }
        sharedViewModel.stateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Content -> showContent()
                is State.Loading -> showLoader(it.message)
                is State.Error -> showError()
            }
        }
    }

    override fun showLoader(message: String) = with(binding) {
        binding.tvLoading.text = message
        loadingGroup.isVisible = true
        tabsGroup.isVisible = false
        errorGroup.isVisible = false
    }

    override fun showContent() = with(binding) {
        loadingGroup.isVisible = false
        tabsGroup.isVisible = true
        errorGroup.isVisible = false
    }

    override fun showError() = with(binding) {
        loadingGroup.isVisible = false
        tabsGroup.isVisible = false
        errorGroup.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}