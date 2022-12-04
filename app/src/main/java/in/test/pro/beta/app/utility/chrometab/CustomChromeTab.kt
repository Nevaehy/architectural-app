package `in`.test.pro.beta.app.utility.chrometab

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

interface CustomChromeTab {

    fun openCustomTab(
        activity: Activity,
        customTabsIntent: CustomTabsIntent,
        uri: Uri
    )
}