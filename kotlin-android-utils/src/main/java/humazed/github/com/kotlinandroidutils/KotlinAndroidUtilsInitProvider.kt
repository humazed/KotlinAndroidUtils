package humazed.github.com.kotlinandroidutils

import android.content.pm.ApplicationInfo
import humazed.github.com.kotlinandroidutils.appctx.appCtx

class KotlinAndroidUtilsInitProvider : InitProvider() {

    override fun onCreate(): Boolean {

        val isDebuggable = 0 != appCtx.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        initTimber(isDebuggable)

        return true
    }
}
