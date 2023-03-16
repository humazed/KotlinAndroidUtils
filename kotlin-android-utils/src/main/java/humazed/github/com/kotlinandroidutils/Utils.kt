package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment

/**
 * User: YourPc
 * Date: 9/15/2017
 */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun Context.dpToPx(dp: Int) =
    Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

fun Fragment.dpToPx(dp: Int) = context?.dpToPx(dp)