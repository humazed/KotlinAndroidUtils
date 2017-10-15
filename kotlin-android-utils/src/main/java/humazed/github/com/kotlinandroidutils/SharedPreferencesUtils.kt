package humazed.github.com.kotlinandroidutils

import android.content.SharedPreferences

/**
 * User: YourPc
 * Date: 9/10/2017
 */
inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}