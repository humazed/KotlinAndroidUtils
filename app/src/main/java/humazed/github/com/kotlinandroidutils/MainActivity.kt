package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun attachBaseContext(newBase: Context?) = super.attachBaseContext(ContextLocale.wrap(newBase!!))

}
