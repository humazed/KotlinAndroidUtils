package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String?) {
    if (!url.isNullOrBlank()) customGlide(context).load(url).into(this)
}

fun ImageView.load(url: Uri?) {
    if (url != null) customGlide(context).load(url).into(this)
}

fun customGlide(context: Context) =
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.timeoutOf(5 * 60 * 1000))
