@file:Suppress("unused", "NOTHING_TO_INLINE")

package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes


inline val Context.defaultSharedPrefs: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)

/**
 * Returns the content view of this Activity if set, null otherwise.
 */
inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)
inline fun <reified T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

inline fun <reified T : View> Dialog.find(@IdRes id: Int): T = findViewById(id)

inline fun <reified T : View> View.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> androidx.fragment.app.Fragment.findOptional(@IdRes id: Int): T? = view?.findViewById(id) as? T

inline fun <reified T : View> Dialog.findOptional(@IdRes id: Int): T? = findViewById(id) as? T

inline val Configuration.portrait: Boolean
    get() = orientation == Configuration.ORIENTATION_PORTRAIT

inline val Configuration.landscape: Boolean
    get() = orientation == Configuration.ORIENTATION_LANDSCAPE

inline val Configuration.long: Boolean
    get() = (screenLayout and Configuration.SCREENLAYOUT_LONG_YES) != 0