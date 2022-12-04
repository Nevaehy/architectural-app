package `in`.test.pro.beta.app.features.test.first

import `in`.test.pro.beta.app.base.BaseFragment
import `in`.test.pro.beta.app.extensions.navigateSafe
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.app.features.test.parent.TestParentFragmentDirections
import `in`.test.pro.beta.app.utility.imageloader.IconUtils
import `in`.test.pro.beta.app.utility.imageloader.ImageOptions
import `in`.test.pro.beta.databinding.FragmentTestFirstChildBinding
import `in`.test.pro.beta.presentation.features.test.children.FirstChildViewModel
import `in`.test.pro.beta.presentation.features.test.children.TestChildrenNavigation
import `in`.test.pro.beta.presentation.features.test.parent.TestParentViewModel
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

class TestFirstChildFragment : BaseFragment() {

    private val sharedViewModel by activityViewModels<TestParentViewModel> { viewModelFactory }
    private val viewModel by viewModels<FirstChildViewModel> { viewModelFactory }

    private var _binding: FragmentTestFirstChildBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
        bindToLifecycle(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentTestFirstChildBinding.inflate(inflater, container, false).also { _binding = it }.root
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
                is TestChildrenNavigation.ShowBottomSheet -> {
                    findNavController().navigateSafe(TestParentFragmentDirections.actionBottomSheetDialog(nav.url))
                }
            }
        }

        sharedViewModel.testData().observe(viewLifecycleOwner) {
            viewModel.setData(it)
            renderData(it)
        }
    }

    private fun renderData(data: String) {
        sharedViewModel.onStartImageLoading()

        IconUtils(requireContext()).loadUrlOnImageView(
            binding.ivTest,
            data,
            ImageOptions(
                successListener = { sharedViewModel.onImageLoadingSuccess() },
                errorListener = { sharedViewModel.onImageLoadingFailure() }
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        fun newInstance(): TestFirstChildFragment = TestFirstChildFragment()
    }
}