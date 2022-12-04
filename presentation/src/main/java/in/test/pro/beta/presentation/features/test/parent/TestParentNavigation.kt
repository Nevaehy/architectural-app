package `in`.test.pro.beta.presentation.features.test.parent

sealed class TestParentNavigation {
    class OpenLink(val url: String = "https://test_link.com") : TestParentNavigation()
}
