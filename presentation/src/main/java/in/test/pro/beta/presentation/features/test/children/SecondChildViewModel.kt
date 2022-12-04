package `in`.test.pro.beta.presentation.features.test.children

import `in`.test.pro.beta.presentation.base.BaseViewModel
import `in`.test.pro.beta.presentation.utils.SingleLiveEvent
import androidx.lifecycle.LiveData
import javax.inject.Inject

// this class might look almost as a duplicate of `FirstChildViewModel`,
// but in real project it probably will have significant differences.
// currently, the goal was to demonstrate the interaction of several fragments within the ViewPager
class SecondChildViewModel @Inject constructor() : BaseViewModel() {
    private val navigation = SingleLiveEvent<TestChildrenNavigation>()
    fun navigation(): LiveData<TestChildrenNavigation> = navigation

    private lateinit var data: String

    // it's not required, but might be a good approach, if we have a big project with multiple state holders that should
    // access nested shared data in certain moments
    fun setData(data: String) {
        this.data = data
    }

    fun onButtonClick() {
        if (::data.isInitialized) {
            navigation.value = TestChildrenNavigation.OpenLink(data)
        }
    }
}