package `in`.test.pro.beta.presentation.utils.resourcemanager

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes stringResId: Int): String

    fun getFormattedString(@StringRes stringResId: Int, vararg params: Any): String

    fun getQuantityString(@PluralsRes stringResId: Int, quantity: Int, vararg params: Any): String

    fun getStringArray(@ArrayRes stringArrayResId: Int): Array<String>

    fun getColor(@ColorRes colorRes: Int): Int

    fun getDimensionPixelSize(@DimenRes dimensionResId: Int): Int

    fun getInteger(@IntegerRes intResId: Int): Int

    fun getFont(@FontRes fontResId: Int): Typeface?

    fun getDrawable(@DrawableRes resId: Int): Drawable?

    fun getColorInInt(color: String): Int
}