package humazed.github.com.kotlinandroidutils

import android.view.View
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.call(progressBar: View?, onResult: (responseBody: T, response: Response<T>) -> Unit) {
    val context = progressBar?.context
    if (context?.isConnected() == true || progressBar == null) {
        progressBar?.show()
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                progressBar?.hide()
                if (response.isSuccessful) {
                    response.body()?.let { onResult(it, response) } ?: e { "Response Null" }
                } else {
                    e { "${response.errorBody()}" }
                    context?.toast(context.getString(R.string.error_happened) ?: "حدث خطأ")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                progressBar?.hide()
                er { t }
                context?.toast(context.getString(R.string.error_happened) ?: "حدث خطأ")
            }
        })
    } else {
        context?.toast(
                context.getString(R.string.no_internet_connection) ?: "لا يوجد اتصال بالانترت")
    }
}

fun <T> Call<T>.call(progressBar: View?, onResult: (responseBody: T) -> Unit) =
        call(progressBar) { responseBody, response -> onResult(responseBody) }


fun <T> Call<T>.onSuccess(onResult: (responseBody: T, response: Response<T>) -> Unit) =
        call(null) { responseBody, response -> onResult(responseBody, response) }

fun <T> Call<T>.onSuccess(onResult: (responseBody: T) -> Unit) =
        onSuccess { responseBody, response -> onResult(responseBody) }