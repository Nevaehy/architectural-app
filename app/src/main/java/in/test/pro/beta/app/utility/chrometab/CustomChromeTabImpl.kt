package `in`.test.pro.beta.app.utility.chrometab

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import javax.inject.Inject

/**
 * This is a helper class to manage the connection to the Custom Tabs Service.
 */
class CustomChromeTabImpl @Inject constructor() : CustomChromeTab {

    override fun openCustomTab(
        activity: Activity,
        customTabsIntent: CustomTabsIntent,
        uri: Uri
    ) {
        try {
            val packageName = CustomTabsHelper.getPackageNameToUse(activity)

            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(activity, uri)
        } catch (e: Exception) {
            // log here
        }
    }
}