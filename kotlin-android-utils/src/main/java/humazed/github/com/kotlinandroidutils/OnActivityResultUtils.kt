package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.gun0912.tedonactivityresult.TedOnActivityResult

fun Context.startActivityForResult(intent: Intent, onResult: (resultCode: Int, data: Intent?) -> Unit) =
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

inline fun <reified T : Activity> Context.startActivityForResult(crossinline onSuccess: (data: Intent?) -> Unit) =
        TedOnActivityResult.with(this)
                .setIntent(Intent(this, T::class.java))
                .setListener { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) onSuccess(data)
                }
                .startActivityForResult()