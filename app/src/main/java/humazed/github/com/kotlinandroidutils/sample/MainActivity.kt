package humazed.github.com.kotlinandroidutils.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import humazed.github.com.kotlinandroidutils.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        codifiedTest()

        logWithTimber()

        d { "Locale.getDefault().language = ${Locale.getDefault().language}" }
        d { "getLanguage() = ${getLanguage()}" }
    }

    private fun logWithTimber() {
        var i = 0

        Timber.d { "log ${i++}" }
        Timber.i { "log ${i++}" }
        Timber.w { "log ${i++}" }
        Timber.e { "log ${i++}" }
        Timber.wtf { "log ${i++}" }

        Timber.tag("Custom tag").d { "log ${i++}" }
        Timber.tag("Custom tag").i { "log ${i++}" }
        Timber.tag("Custom tag").w { "log ${i++}" }
        Timber.tag("Custom tag").e { "log ${i++}" }
        Timber.tag("Custom tag").wtf { "log ${i++}" }

        d { "log ${i++}" }
        i { "log ${i++}" }
        w { "log ${i++}" }
        e { "log ${i++}" }
        wtf { "log ${i++}" }

        val exception = NullPointerException("12345")
        e(exception)
        e(exception) { "exception" }
    }

    private fun codifiedTest() {
        val decode = Language::class.decode("ar")
        val decode1 = Language::class.decode("en")
        val decode3: Language = try {
            Language::class.decode("aa")
        } catch (e: Exception) {
            Language::class.decode("ar")
        }

        d { "decode = $decode" }
        d { "decode1 = $decode1" }
        d { "decode3 = $decode3" }
    }

    override fun onResume() {
        super.onResume()
        onResumeLocaleDelegate()
    }

    override fun attachBaseContext(newBase: Context?) = super.attachBaseContext(wrap(newBase!!))
}
