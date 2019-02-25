package humazed.github.com.kotlinandroidutils

class KotlinAndroidUtilsInitProvider : InitProvider() {

    override fun onCreate(): Boolean {

        initTimber(BuildConfig.DEBUG)

        return true
    }
}
