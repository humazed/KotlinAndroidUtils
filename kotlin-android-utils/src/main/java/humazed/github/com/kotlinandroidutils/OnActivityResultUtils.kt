package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.gun0912.tedonactivityresult.TedOnActivityResult

fun Context.startActivityForResult(
    intent: Intent,
    onResult: (resultCode: Int, data: Intent?) -> Unit
) =
    TedOnActivityResult.with(this)
        .setIntent(intent)
        .setListener { resultCode, data -> onResult(resultCode, data) }
        .startActivityForResult()

fun Context.startActivityForResult(intent: Intent, onSuccess: (data: Intent?) -> Unit) =
    TedOnActivityResult.with(this)
        .setIntent(intent)
        .setListener { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) onSuccess(data)
        }
        .startActivityForResult()


inline fun <reified T : Activity> Context.startActivityForResult(
    vararg params: Pair<String, Any?>,
    crossinline onResult: (resultCode: Int, data: Intent?) -> Unit
) {
    TedOnActivityResult.with(this)
        .setIntent(createIntent(this, T::class.java, params))
        .setListener { resultCode, data -> onResult(resultCode, data) }
        .startActivityForResult()
}

inline fun <reified T : Activity> Context.startActivityForResult(
    vararg params: Pair<String, Any?>,
    crossinline onSuccess: (data: Intent?) -> Unit
) {
    TedOnActivityResult.with(this)
        .setIntent(createIntent(this, T::class.java, params))
        .setListener { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) onSuccess(data)
        }
        .startActivityForResult()
}