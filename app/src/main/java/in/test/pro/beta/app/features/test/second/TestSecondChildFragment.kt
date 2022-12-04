package `in`.test.pro.beta.app.features.test.second

import `in`.test.pro.beta.app.base.BaseFragment
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.databinding.FragmentTestSecondChildBinding
import `in`.test.pro.beta.presentation.features.test.children.SecondChildViewModel
import `in`.test.pro.beta.presentation.features.test.children.TestChildrenNavigation
import `in`.test.pro.beta.presentation.features.test.parent.TestParentViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

// this class might look almost as a duplicate of `TestFirstChildFragment`,
// but in real project it probably will have significant differences.
// currently, the goal was to demonstrate the interaction of several fragments within the ViewPager
class TestSecondChildFragment : BaseFragment() {

    private val sharedViewModel by activityViewModels<TestParentViewModel> { viewModelFactory }
    private val viewModel by viewModels<SecondChildViewModel> { viewModelFactory }

    private var _binding: FragmentTestSecondChildBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentTestSecondChildBinding.inflate(inflater, container, false).also { _binding = it}.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.btnTest.setOnClickListener { viewModel.onButtonClick() }
    }

    private fun initObservers() {
        viewModel.navigation().observe(viewLifecycleOwner) { nav ->
            when (nav) {
                is TestChildrenNavigation.OpenLink -> launchChromeTab(nav.url)
            }
        }

        sharedViewModel.testData().observe(viewLifecycleOwner) {
            viewModel.setData(it)
            renderData(it)
        }
    }

    private fun renderData(data: String) {
        binding.tvTestLink.text = data
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(): TestSecondChildFragment = TestSecondChildFragment()
    }
}