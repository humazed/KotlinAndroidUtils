package humazed.github.com.kotlinandroidutils

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * User: YourPc
 * Date: 3/12/2018
 */

inline fun FragmentManager.transaction(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commit()
}