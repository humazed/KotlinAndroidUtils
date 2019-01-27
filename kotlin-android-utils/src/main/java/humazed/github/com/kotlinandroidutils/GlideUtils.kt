package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(context: Context, url: String?) {
    if (!url.isNullOrBlank()) Glide.with(context).load(url).into(this)
}