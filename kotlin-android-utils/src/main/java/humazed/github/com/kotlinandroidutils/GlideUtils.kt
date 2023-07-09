package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

fun ImageView.load(
    url: String?,
    options: RequestOptions.() -> RequestOptions = { RequestOptions() }
) = customGlide(context, options).also {
    if (!url.isNullOrBlank()) it.load(url).into(this)
}

fun ImageView.load(
    url: Uri?,
    options: RequestOptions.() -> RequestOptions = { RequestOptions() }
) = customGlide(context, options).also {
    if (url != null) it.load(url).into(this)
}

fun ImageView.load(
    file: File?,
    options: RequestOptions.() -> RequestOptions = { RequestOptions() }
) = customGlide(context, options).also {
    if (file != null) it.load(file).into(this)
}

fun customGlide(
    context: Context,
    options: RequestOptions.() -> RequestOptions
) = Glide.with(context)
    .applyDefaultRequestOptions(
        RequestOptions.timeoutOf(5 * 60 * 1000).apply { options() }
    )


/**
 * a shortcut for placeholder, error and fallback
 */
fun RequestOptions.default(@DrawableRes resourceId: Int): RequestOptions {
    return placeholder(resourceId)
        .error(resourceId)
        .fallback(resourceId)
}