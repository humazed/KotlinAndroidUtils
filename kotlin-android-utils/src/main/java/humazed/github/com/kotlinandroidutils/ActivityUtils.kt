package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.os.Build
import android.view.View


fun Activity.restart() {
    val intent = intent
    finish()
    startActivity(intent)
}

/**
 * should be called before super.onCreate(savedInstanceState)
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
