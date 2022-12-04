package `in`.test.pro.beta.presentation.features.test.parent

import `in`.test.pro.beta.domain.base.Outcome
import `in`.test.pro.beta.domain.features.test.TestInteractor
import `in`.test.pro.beta.domain.features.test.models.DTestRequestData
import `in`.test.pro.beta.presentation.R
import `in`.test.pro.beta.presentation.base.BaseViewModel
import `in`.test.pro.beta.presentation.base.State
import `in`.test.pro.beta.presentation.utils.SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class TestParentViewModel @Inject constructor(
    private val testInteractor: TestInteractor
) : BaseViewModel() {

    private var testJob: Job? = null

    private val navigation = SingleLiveEvent<TestParentNavigation>()
    fun navigation(): LiveData<TestParentNavigation> = navigation

    private val testData = MutableLiveData<String>()
    fun testData(): LiveData<String> = testData

    // in more complex project `startOnce` could be used instead
    init {
        loadTestData { state -> stateLiveData.value = state }
    }

    fun retry() {
        loadTestData { state -> stateLiveData.value = state }
    }

    private fun loadTestData(onStateChange: (state: State) -> Unit) {
        testJob?.cancel()
        testJob = launch(viewModelScope) {
            onStateChange.invoke(State.Loading(resourceManager.getString(R.string.getting_response_description)))
            when (val outcome = testInteractor.getTestData(getRequestData())) {
                is Outcome.Success -> {
                    stateLiveData.value = State.Content
                    // sometimes LiveData updates called in a rapid way don't work
                    delay(STATE_DELAY)
                    testData.value = outcome.data.url
                }
                is Outcome.Error -> onStateChange.invoke(State.Error(outcome.reason.toString()))
                is Outcome.NetworkConnection -> onStateChange.invoke(State.Error(outcome.cause?.message))
            }
        }
    }

    fun onStartImageLoading() {
        stateLiveData.value = State.Loading(resourceManager.getString(R.string.loading_and_rendering_description))
    }

    fun onImageLoadingSuccess() {
        stateLiveData.value = State.Content
    }

    fun onImageLoadingFailure() {
        stateLiveData.value = State.Error()
    }

    fun onUrlClick() {
        navigation.value = TestParentNavigation.OpenLink()
    }

    private fun getRequestData() = DTestRequestData(
        prompt = PROMPT,
        n = N,
        size = SIZE
    )

    companion object {
        private const val PROMPT = "THE TEXT DESCRIPTION"
        private const val N = 1
        private const val SIZE = "1024x1024"
        private const val STATE_DELAY = 15L
    }
}