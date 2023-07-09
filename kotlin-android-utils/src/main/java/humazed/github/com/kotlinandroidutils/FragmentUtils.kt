package humazed.github.com.kotlinandroidutils

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 * User: YourPc
 * Date: 3/12/2018
 */

inline fun androidx.fragment.app.FragmentManager.transaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commit()
}

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}