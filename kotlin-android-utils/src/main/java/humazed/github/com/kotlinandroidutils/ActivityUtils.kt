package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View


fun Activity.restart() {
    val intent = intent
    finish()
    startActivity(intent)
}

/**
 * should be called before [setContentView()]
 */
fun Activity.setImmersiveScreen() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}

/**
 * Example:
 * ```
 * start<MainActivity> {
 *    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
 * }
 * ```
 */
inline fun <reified A : Activity> Context.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(this, A::class.java).apply(configIntent))
}

/**
 * Example:
 * ```
 * startActivity(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) {
 *     data = Uri.fromParts("package", packageName, null)
 * }
 * ```
 */
inline fun Context.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}