package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import java.util.*


class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Codified test
        val decode = Language::class.decode("ar")
        val decode1 = Language::class.decode("en")
        val decode3: Language = try {
            Language::class.decode("aa")
        } catch (e: Exception) {
            Language::class.decode("ar")
        }

        warn { "decode = ${decode}" }
        warn { "decode1 = ${decode1}" }
        warn { "decode3 = ${decode3}" }

        warn { "Locale.getDefault().language = ${Locale.getDefault().language}" }
        warn { "getLanguage() = ${getLanguage()}" }

    }

    override fun attachBaseContext(newBase: Context?) = super.attachBaseContext(ContextLocale.wrap(newBase!!))

}
