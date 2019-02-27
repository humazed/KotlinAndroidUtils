package humazed.github.com.kotlinandroidutils

import android.view.View
import android.widget.EditText
import humazed.github.com.kotlinandroidutils.appctx.appCtx
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

fun <T> Call<T>.call(progressBar: View?, onResult: (responseBody: T?, response: Response<T>) -> Unit) {
    val context = progressBar?.context ?: appCtx
    if (context.isConnected()) {
        progressBar?.show()
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                progressBar?.hide()
                onResult(response.body(), response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                progressBar?.hide()
                er { t }
                context.toast(context.getString(R.string.error_happened))
            }
        })
    } else {
        progressBar?.hide()
        context.toast(context.getString(R.string.no_internet_connection))
    }
}

fun <T> Call<T>.call(progressBar: View?, onResult: (response: T) -> Unit) {
    val context = progressBar?.context ?: appCtx
    call(progressBar) { responseBody, response ->
        if (response.isSuccessful) {
            responseBody?.let { onResult(it) } ?: e { "Response Null" }
        } else {
            e { "${response.errorBody()?.string()}" }
            context.toast(context.getString(R.string.error_happened))
        }
    }
}


fun <T> Call<T>.onSuccess(onResult: (responseBody: T?, response: Response<T>) -> Unit) =
        call(null) { responseBody, response -> onResult(responseBody, response) }

fun <T> Call<T>.onSuccess(onResult: (response: T) -> Unit) =
        call(null) { responseBody -> onResult(responseBody) }


// Multipart helpers
fun EditText.textPart(): RequestBody = MultipartBody.create(MultipartBody.FORM, text.toString())

fun String.part(): RequestBody = MultipartBody.create(MultipartBody.FORM, this)
fun Int.part() = toString().part()
fun Double.part() = toString().part()

@JvmName("stringPart")
fun List<String>.part() = map { it.part() }

@JvmName("intPart")
fun List<Int>.part() = map { it.part() }

@JvmName("doublePart")
fun List<Double>.part() = map { it.part() }

fun File.part(requestName: String, mimeType: String = "image/*"): MultipartBody.Part {
    // okHttp doesn't accept non ascii chars and crashes the app
    val asciiName = name.replace(Regex("[^A-Za-z0-9 ]"), "")
    val requestFile = RequestBody.create(MediaType.parse(mimeType), this)
    return MultipartBody.Part.createFormData(requestName, asciiName, requestFile)
}

@JvmName("filePart")
fun List<File>.part(requestName: String, mimeType: String = "image/*") = map { it.part(requestName, mimeType) }
