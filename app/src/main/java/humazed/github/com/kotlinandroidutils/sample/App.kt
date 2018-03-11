package humazed.github.com.kotlinandroidutils.sample

import android.app.Application
import humazed.github.com.kotlinandroidutils.initTimber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber(null)

    }
}
