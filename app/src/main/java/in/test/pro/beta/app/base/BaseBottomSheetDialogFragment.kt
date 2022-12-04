package `in`.test.pro.beta.app.base

import `in`.test.pro.beta.R
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.min
import kotlin.math.roundToInt

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    // When implementing these methods and inflating use attachToRoot false

    abstract fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        attachToRoot: Boolean = false
    ): View

    abstract fun getTopContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        attachToRoot: Boolean = false
    ): View?

    abstract fun getBotContentView(
        inflater: LayoutInflater,
        container: ViewGroup,
        attachToRoot: Boolean = false
    ): View?

    abstract fun showAboveKeyboard(): Boolean

    /**
     * Returns the maximum height percentage that the sheet can be displayed on the screen.
     * 60% is default.
     */
    open fun getMaxHeightPercentage(): Float = 0.6f

    /**
     * Returns the height of the collapsed bottom sheet in percents [0..1f]
     * Override to set peek height explicitly,
     * for the case when Bottom sheet view's layout.height is measured differently while it's content is unchanged.
     */
    open val peekHeightPercentage = 0f

    /**
     * Returns true if botContentView should be pinned to the bottom.
     */
    open val shouldPinBottomContent = false

    /**
     * Returns true if bottomContent bg color should be the same as Bottom sheet background color.
     */
    open val shouldSetBottomBgColorDefault = false

    protected var nestedScrollView: NestedScrollView? = null

    private var botContentView: View? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener { dialogInterface ->
                (dialogInterface as? BottomSheetDialog)
                    ?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                    ?.let { view ->
                        updateDialogHeight(context, false)
                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base_bottom_sheet_dialog, container, false)

        if (view is ViewGroup) {
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            nestedScrollView = view.findViewById(R.id.nestedScrollView)

            nestedScrollView?.let {
                val contentView = getContentView(inflater, it)
                it.removeAllViews()
                if (contentView.paddingBottom == 0) {
                    contentView.setPadding(
                        contentView.paddingLeft,
                        contentView.paddingTop,
                        contentView.paddingRight,
                        25
                    )
                }
                it.addView(contentView, layoutParams)
            }

            val topContentView = getTopContentView(inflater, view)
            if (topContentView != null) {
                val handleView = view.findViewById<View>(R.id.viewHandle)
                view.addView(topContentView, view.indexOfChild(handleView) + 1, layoutParams)
            }

            botContentView = getBotContentView(inflater, view)
        }

        return view
    }

    protected fun updateDialogHeight(
        context: Context,
        animate: Boolean = true
    ) {
        (dialog as? BottomSheetDialog)
            ?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            ?.let { view ->
                val displayMetrics = context.resources.displayMetrics
                val screenHeight = displayMetrics.heightPixels
                view.doOnPreDraw { layout ->
                    val maxHeight = (screenHeight * getMaxHeightPercentage()).roundToInt()
                    val capHeight = layout.height >= maxHeight
                    val peekHeight = when {
                        capHeight -> maxHeight
                        peekHeightPercentage != 0f -> min((maxHeight * peekHeightPercentage).toInt(), layout.height)
                        else -> layout.height
                    }

                    layout.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                        this.height = if (capHeight) maxHeight else ViewGroup.LayoutParams.WRAP_CONTENT
                    }

                    val bottomSheetBehavior = BottomSheetBehavior.from(layout)
                    if (animate) {
                        ObjectAnimator.ofInt(bottomSheetBehavior, "peekHeight", peekHeight).start()
                    } else {
                        bottomSheetBehavior.peekHeight = peekHeight
                    }

                    if (shouldPinBottomContent) {
                        pinBottomContentToBottom(bottomSheetBehavior)
                    }
                }
            }
    }

    private var sheetCallback: BottomSheetBehavior.BottomSheetCallback? = null

    /**
     * Listens to the bottom sheet slide
     *
     * @param bottomSheetBehavior
     */
    private fun pinBottomContentToBottom(bottomSheetBehavior: BottomSheetBehavior<*>) {
        if (botContentView == null) {
            return
        }
        /*set initial botContentView offset based on sheet's peekHeight*/
        botContentView?.post { setBottomContentOffset(bottomSheetBehavior.peekHeight) }

        sheetCallback = sheetCallback ?: object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {}

            override fun onSlide(bottomSheetView: View, slideOffset: Float) {
                setBottomContentOffset(
                    bottomSheetBehavior.peekHeight,
                    bottomSheetView.height,
                    slideOffset
                )
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(sheetCallback!!)
    }

    /**
     * Compensates botContentView vertical shift and keeps it always at the screen's bottom,
     * works when Bottom sheet is between COLLAPSED and EXPANDED states inclusive.
     * [slideOffset] values can be from -1 to 1, depending on the Bottom sheet state.
     * COLLAPSED state corresponds to [slideOffset] = 0f.
     * EXPANDED state corresponds to [slideOffset] = 1f.
     *
     * @param sheetHeightPeek
     * @param sheetHeightFull
     * @param slideOffset
     */

    private fun setBottomContentOffset(
        sheetHeightPeek: Int,
        sheetHeightFull: Int = 0,
        slideOffset: Float = 0f
    ) {
        botContentView?.apply {
            val shift: Float =
                if (slideOffset > 0) {
                    ((1 - slideOffset) * sheetHeightPeek) + (slideOffset * sheetHeightFull) - this.height
                } else {
                    sheetHeightPeek - this.height.toFloat()
                }
            this.y = shift
        }
    }
}