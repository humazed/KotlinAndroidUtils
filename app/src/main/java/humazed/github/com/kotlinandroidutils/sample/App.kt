package humazed.github.com.kotlinandroidutils.sample

import android.app.Application
import humazed.github.com.kotlinandroidutils.Language
import humazed.github.com.kotlinandroidutils.setLanguage

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        setLanguage(Language.ENGLISH)
    }
}
