package `in`.test.pro.beta.app.features.test.parent

import `in`.test.pro.beta.R
import `in`.test.pro.beta.app.base.BaseActivity
import `in`.test.pro.beta.app.extensions.viewComponent
import `in`.test.pro.beta.databinding.ActivityTestParentBinding
import `in`.test.pro.beta.presentation.features.test.parent.TestParentViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment

class TestParentActivity : BaseActivity() {

    private val sharedViewModel by viewModels<TestParentViewModel> { viewModelFactory }
    private var _binding: ActivityTestParentBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestParentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewComponent().inject(this)
        bindToLifecycle(sharedViewModel)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        navController.setGraph(R.navigation.nav_graph_test)
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, TestParentActivity::class.java)
            context.startActivity(intent)
        }
    }
}