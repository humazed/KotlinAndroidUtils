@file:Suppress("NOTHING_TO_INLINE")

package humazed.github.com.kotlinandroidutils

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

var View.visible
    get() = visibility == VISIBLE
    set(value) {
        visibility = if (value) VISIBLE else GONE
    }

/**
 * gone: Boolean = true
 */
inline fun View.hide(gone: Boolean = true) {
    visibility = if (gone) GONE else INVISIBLE
}

inline fun View.show() {
    visibility = VISIBLE
}

inline fun View.setWidth(width: Int) {
    val params = layoutParams
    params.width = width
    layoutParams = params
}

inline fun View.setHeight(height: Int) {
    val params = layoutParams
    params.height = height
    layoutParams = params
}

inline fun View.setSize(width: Int, height: Int) {
    val params = layoutParams
    params.width = width
    params.height = height
    layoutParams = params
}

fun View.setBackgroundTint(@ColorRes colorRes: Int) {
    ViewCompat.setBackgroundTintList(
            this, ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
    )
}

inline fun EditText.onTextChange(crossinline f: (s: CharSequence, start: Int, before: Int, count: Int) -> Unit) {
    val listener = object : KoiTextWatcher() {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            f(s, start, before, count)
        }
    }
    this.addTextChangedListener(listener)
}

abstract class KoiTextWatcher : TextWatcher {
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}
}
