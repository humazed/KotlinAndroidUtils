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

        d { "Locale.getDefault().language = ${Locale.getDefault().language}" }
        d { "getLanguage() = ${getLanguage()}" }

        codifiedTest()

        logWithTimber()
    }

    private fun logWithTimber() {
        var i = 0

        d { "log ${i++}" }
        i { "log ${i++}" }
        w { "log ${i++}" }
        e { "log ${i++}" }
        wtf { "log ${i++}" }


        //        val exception = NullPointerException("12345")
        //        er { exception }
        //        er(exception) { "exception" }
        //        er(null)

        "INNER".apply {
            5?.let {
                d { "this = ${this}" }

                //                val exception = NullPointerException("12345")
                //                er { exception }
                //                er({ exception }) { "dsds" }
                //                er(exception) { "exception" }
                //                er(null)
            }
        }

        logFromFile()

        LogTest.logFromJava()
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

private fun logFromFile() {
    var i = 0

    d { "logFromFile ${i++}" }
    i { "logFromFile ${i++}" }
    w { "logFromFile ${i++}" }
    e { "logFromFile ${i++}" }
    wtf { "logFromFile ${i++}" }
}
