package humazed.github.com.kotlinandroidutils.sample

import androidx.multidex.MultiDexApplication
import humazed.github.com.kotlinandroidutils.Language
import humazed.github.com.kotlinandroidutils.setLanguage

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        setLanguage(Language.ENGLISH)
    }
}
