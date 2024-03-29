package humazed.github.com.kotlinandroidutils

import android.view.View
import android.widget.EditText
import humazed.github.com.kotlinandroidutils.appctx.appCtx
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

fun <T> Call<T>.call(
    progressBar: View?,
    onResult: (responseBody: T?, response: Response<T>) -> Unit,
    onFailure: ((t: Throwable) -> Unit)? = null
) {
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
                er({ t }, { "Url: ${call.request().url}" })

                if (onFailure == null) {
                    context.toast(context.getString(R.string.error_happened))
                } else {
                    onFailure(t)
                }
            }
        })
    } else {
        progressBar?.hide()
        context.toast(context.getString(R.string.no_internet_connection))
    }
}

fun <T> Call<T>.call(
    progressBar: View?,
    onResult: (response: T) -> Unit,
    onFailure: ((t: Throwable?, errorBody: ResponseBody?) -> Unit)? = null
) {
    val context = progressBar?.context ?: appCtx
    call(progressBar, { responseBody, response ->
        val url = response.raw().request.url
        if (response.isSuccessful) {
            responseBody?.let { onResult(it) } ?: e { "Url: $url Response Null" }
        } else {
            e { "Url: $url \nErrorBody: ${response.errorBody()?.string()}" }

            if (onFailure == null) {
                context.toast(context.getString(R.string.error_happened))
            } else {
                onFailure(null, response.errorBody())
            }
        }
    }, { t -> onFailure?.invoke(t, null) })
}


fun <T> Call<T>.onSuccess(onResult: (responseBody: T?, response: Response<T>) -> Unit) =
    call(
        null,
        onResult = { responseBody, response -> onResult(responseBody, response) },
        onFailure = null
    )

fun <T> Call<T>.onSuccess(onResult: (response: T) -> Unit) =
    call(
        null,
        onResult = { responseBody -> onResult(responseBody) },
        onFailure = null
    )


// Multipart helpers
fun EditText.textPart(): RequestBody = text.toString().toRequestBody(MultipartBody.FORM)

//        MultipartBody.create(MultipartBody.FORM, text.toString())

fun String.part(): RequestBody = this.toRequestBody(MultipartBody.FORM)
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
    val requestFile = this.asRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(requestName, asciiName, requestFile)
}

@JvmName("filePart")
fun List<File>.part(requestName: String, mimeType: String = "image/*") =
    map { it.part(requestName, mimeType) }
