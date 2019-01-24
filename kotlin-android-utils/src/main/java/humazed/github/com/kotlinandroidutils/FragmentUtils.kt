package humazed.github.com.kotlinandroidutils

/**
 * User: YourPc
 * Date: 3/12/2018
 */

inline fun androidx.fragment.app.FragmentManager.transaction(func: androidx.fragment.app.FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commit()
}