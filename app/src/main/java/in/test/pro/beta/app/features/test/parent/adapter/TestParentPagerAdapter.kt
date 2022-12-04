package `in`.test.pro.beta.app.features.test.parent.adapter

import `in`.test.pro.beta.app.features.test.first.TestFirstChildFragment
import `in`.test.pro.beta.app.features.test.second.TestSecondChildFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TestParentPagerAdapter(
    childFragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(childFragmentManager, lifecycle) {

    private val fragments by lazy { initFragments() }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    private fun initFragments(): List<Fragment> {
        return listOf(
            TestFirstChildFragment.newInstance(),
            TestSecondChildFragment.newInstance()
        )
    }
}