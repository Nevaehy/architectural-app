package `in`.test.pro.beta.app.utility.imageloader

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment

sealed class ImageParent {
    data class ParentActivity(val activity: Activity) : ImageParent()
    data class ParentFragment(val fragment: Fragment) : ImageParent()
    data class ParentView(val view: View) : ImageParent()
}