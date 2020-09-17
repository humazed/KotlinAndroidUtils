package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun ImageView.load(url: String?, options: RequestOptions.() -> Unit = {}) = customGlide(context, options).also {
    if (!url.isNullOrBlank()) it.load(url).into(this)
}

fun ImageView.load(url: Uri?, options: RequestOptions.() -> Unit = {}) = customGlide(context, options).also {
    if (url != null) it.load(url).into(this)
}

fun ImageView.load(file: File?, options: RequestOptions.() -> Unit = {}) = customGlide(context, options).also {
    if (file != null) it.load(file).into(this)
}

fun customGlide(context: Context, options: RequestOptions.() -> Unit) =
        Glide.with(context)
                .applyDefaultRequestOptions(RequestOptions.timeoutOf(5 * 60 * 1000).apply(options))
