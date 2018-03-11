@file:Suppress("unused")

package humazed.github.com.kotlinandroidutils

import android.os.Bundle


inline fun bundle(body: Bundle.() -> Unit): Bundle {
    val bundle = Bundle()
    bundle.body()
    return bundle
}

inline fun bundle(loader: ClassLoader, body: Bundle.() -> Unit): Bundle {
    val bundle = Bundle(loader)
    bundle.body()
    return bundle
}

inline fun bundle(capacity: Int, body: Bundle.() -> Unit): Bundle {
    val bundle = Bundle(capacity)
    bundle.body()
    return bundle
}

inline fun bundle(b: Bundle?, body: Bundle.() -> Unit): Bundle {
    val bundle = Bundle(b)
    bundle.body()
    return bundle
}
