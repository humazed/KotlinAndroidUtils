package humazed.github.com.kotlinandroidutils

import android.content.pm.ApplicationInfo
import com.jakewharton.threetenabp.AndroidThreeTen
import humazed.github.com.kotlinandroidutils.appctx.appCtx

class KotlinAndroidUtilsInitProvider : InitProvider() {

    override fun onCreate(): Boolean {

        val isDebuggable = 0 != appCtx.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        initTimber(isDebuggable)

        AndroidThreeTen.init(appCtx)

        return true
    }
}
