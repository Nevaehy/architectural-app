package `in`.test.pro.beta.domain.features.test

sealed class GetTestError {
    data class BackendError(val code: Int, val message: String) : GetTestError()
}