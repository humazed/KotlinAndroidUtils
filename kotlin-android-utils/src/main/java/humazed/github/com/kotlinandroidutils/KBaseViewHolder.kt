package humazed.github.com.kotlinandroidutils

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * implements [LayoutContainer] so it can cache the viewHolder see [https://github.com/Kotlin/KEEP/blob/master/proposals/android-extensions-entity-caching.md#view-holder-pattern]
 */
class KBaseViewHolder(view: View) : BaseViewHolder(view), LayoutContainer {
    override val containerView: View? = view
}