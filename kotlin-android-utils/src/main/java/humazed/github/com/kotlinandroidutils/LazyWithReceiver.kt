package humazed.github.com.kotlinandroidutils

import java.util.*
import kotlin.reflect.KProperty

/**
 * copied from: https://stackoverflow.com/questions/34391255/this-reference-in-a-lazy-initializer-of-kotlin-extension-property
 */
class LazyWithReceiver<This, Return>(val initializer: This.() -> Return) {
    private val values = WeakHashMap<This, Return>()

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any, property: KProperty<*>): Return = synchronized(values)
    {
        thisRef as This
        return values.getOrPut(thisRef) { thisRef.initializer() }
    }
}