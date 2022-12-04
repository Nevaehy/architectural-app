package `in`.test.pro.beta.presentation.utils.resourcemanager

import `in`.test.pro.beta.domain.di.ModuleContext
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import javax.inject.Inject

class ResourceManagerImpl @Inject constructor(
    @ModuleContext private val context: Context
) : ResourceManager {

    override fun getString(stringResId: Int): String {
        return context.getString(stringResId)
    }

    override fun getFormattedString(stringResId: Int, vararg params: Any): String {
        return context.resources.getString(stringResId, *params)
    }

    override fun getQuantityString(stringResId: Int, quantity: Int, vararg params: Any): String {
        return context.resources.getQuantityString(stringResId, quantity, *params)
    }

    override fun getStringArray(stringArrayResId: Int): Array<String> {
        return context.resources.getStringArray(stringArrayResId)
    }

    override fun getColor(colorRes: Int): Int {
        return ContextCompat.getColor(context, colorRes)
    }

    override fun getDimensionPixelSize(dimensionResId: Int): Int {
        return context.resources.getDimensionPixelSize(dimensionResId)
    }

    override fun getInteger(intResId: Int): Int {
        return context.resources.getInteger(intResId)
    }

    override fun getFont(fontResId: Int): Typeface? {
        return ResourcesCompat.getFont(context, fontResId)
    }

    override fun getDrawable(resId: Int): Drawable? {
        return AppCompatResources.getDrawable(context, resId)
    }

    override fun getColorInInt(color: String): Int {
        return context.resources.getIdentifier(color, "color", context.applicationContext.packageName)
    }
}