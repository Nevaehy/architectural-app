package `in`.test.pro.beta.app.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

/**
 * This extension fixes the crash when the user triggers (Dialog) Fragment show repeatedly.
 * IllegalArgumentException: Navigation action/destination xxx cannot be found from the current destination yyy */

fun NavController.navigateSafe(
    direction: NavDirections,
    navOptions: NavOptions? = null
): Boolean = currentDestination?.getAction(direction.actionId)?.let {
    navigate(direction, navOptions)
    true
} ?: false
