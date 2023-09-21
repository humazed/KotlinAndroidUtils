@file:Suppress("unused")

package humazed.github.com.kotlinandroidutils


import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment

/**
 * Execute [f] on the application UI thread.
 */
fun Context.runOnUiThread(f: Context.() -> Unit) {
    if (Looper.getMainLooper() === Looper.myLooper()) f() else ContextHelper.handler.post { f() }
}

/**
 * Execute [f] on the application UI thread.
 */
inline fun Fragment.runOnUiThread(crossinline f: () -> Unit) {
    activity?.runOnUiThread { f() }
}

private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
}