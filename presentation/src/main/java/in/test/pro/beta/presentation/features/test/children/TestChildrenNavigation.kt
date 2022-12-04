package `in`.test.pro.beta.presentation.features.test.children

sealed class TestChildrenNavigation {
    class ShowBottomSheet(val url: String) : TestChildrenNavigation()
    class OpenLink(val url: String) : TestChildrenNavigation()
}
