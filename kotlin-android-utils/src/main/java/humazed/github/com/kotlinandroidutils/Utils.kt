package humazed.github.com.kotlinandroidutils

/**
 * User: YourPc
 * Date: 9/15/2017
 */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}
