package `in`.test.pro.beta.app.extensions

import `in`.test.pro.beta.app.MyApplication
import `in`.test.pro.beta.app.base.BaseBottomSheetDialogFragment
import `in`.test.pro.beta.app.base.BaseDialogFragment
import `in`.test.pro.beta.app.base.BaseFragment
import android.os.Bundle
import androidx.fragment.app.Fragment

fun BaseFragment.viewComponent() =
    (activity?.application as MyApplication).dataComponent!!.getViewComponentBuilder()
        .context(requireContext())
        .build()

fun BaseDialogFragment.viewComponent() =
    (activity?.application as MyApplication).dataComponent!!.getViewComponentBuilder()
        .context(requireContext())
        .build()

fun BaseBottomSheetDialogFragment.viewComponent() =
    (activity?.application as MyApplication).dataComponent!!.getViewComponentBuilder()
        .context(requireContext())
        .build()
