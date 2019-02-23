package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit

fun ImageView.load(url: String?) {
    if (!url.isNullOrBlank()) Glide.with(context).load(url).into(this)
}

fun ImageView.load(url: Uri?) {
    if (url != null) Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions.timeoutOf(5 * 60 * 1000).diskCacheStrategy(DiskCacheStrategy.ALL))
            .load(url)
            .apply(RequestOptions.timeoutOf(5 * 60 * 1000))
            .into(this)
}