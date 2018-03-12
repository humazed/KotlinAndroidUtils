package humazed.github.com.kotlinandroidutils

import android.app.Activity

fun Activity.restart() {
    val intent = intent
    finish()
    startActivity(intent)
}
