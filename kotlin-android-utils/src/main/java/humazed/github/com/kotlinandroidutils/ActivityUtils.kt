package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

fun Activity.restart() {
    val intent = intent
    finish()
    startActivity(intent)
}

inline fun FragmentManager.transaction(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commit()
}