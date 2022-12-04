package `in`.test.pro.beta.app.features.test.infodialog

import `in`.test.pro.beta.app.common.BottomSheetDialogFragment
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.databinding.FragmentInfoScreenDialogBinding
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

class InfoScreenDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentInfoScreenDialogBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val args: InfoScreenDialogFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewComponent().inject(this)
    }

    override fun getTopContentView(inflater: LayoutInflater, container: ViewGroup, attachToRoot: Boolean): View? = null

    override fun getBotContentView(inflater: LayoutInflater, container: ViewGroup, attachToRoot: Boolean): View? = null

    override fun showAboveKeyboard(): Boolean = false

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup, attachToRoot: Boolean): View {
        _binding = FragmentInfoScreenDialogBinding.inflate(inflater, container, attachToRoot)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = args.body
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}