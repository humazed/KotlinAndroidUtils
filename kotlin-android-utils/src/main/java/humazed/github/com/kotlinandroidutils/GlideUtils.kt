package humazed.github.com.kotlinandroidutils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String?) {
    if (!url.isNullOrBlank()) Glide.with(context).load(url).into(this)
}

fun ImageView.load(url: Uri?) {
    if (url != null) Glide.with(context).load(url).into(this)
}

