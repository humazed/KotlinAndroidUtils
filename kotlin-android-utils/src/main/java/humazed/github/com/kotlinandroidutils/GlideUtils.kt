package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun ImageView.load(url: String?) {
    if (!url.isNullOrBlank()) customGlide(context).load(url).into(this)
}

fun ImageView.load(url: Uri?) {
    if (url != null) customGlide(context).load(url).into(this)
}

fun ImageView.load(file: File?) {
    if (file != null) customGlide(context).load(file).into(this)
}

fun customGlide(context: Context) =
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.timeoutOf(5 * 60 * 1000))
