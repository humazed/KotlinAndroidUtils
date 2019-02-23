package humazed.github.com.kotlinandroidutils.appctx

import androidx.annotation.CallSuper
import humazed.github.com.kotlinandroidutils.InitProvider

/**
 * Initializes [appCtx] so it can be used where any [Context] can be used.
 *
 * If you use [appCtx] in another process than the default one. This will not be invoked.
 * The library will attempt to initialize [appCtx] using reflection instead. If you want to avoid
 * this, you can declare a subclass of this class in your manifest with the name of the process
 * properly specified to get it Automatically initialized. Alternatively, you can in your properly
 * declared custom [Application] call [injectAsAppCtx] passing `this` reference from the constructor
 * or the [Application.onCreate] method.
 */
open class AppCtxInitProvider : InitProvider() {
    @CallSuper
    override fun onCreate() = true.also { _ -> context!!.injectAsAppCtx() }
}