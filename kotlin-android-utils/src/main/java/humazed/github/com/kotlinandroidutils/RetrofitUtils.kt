package humazed.github.com.kotlinandroidutils

import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.call(progressBar: View?, onResult: (response: T) -> Unit) {
    progressBar?.show()
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            progressBar?.hide()
            if (response.isSuccessful) response.body()?.let { onResult(it) }
                    ?: e { "Response Null" }
            else e { "${response.errorBody()}" }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            progressBar?.hide()
            er { t }
        }
    })
}

fun <T> Call<T>.onSuccess(onResult: (response: T) -> Unit) = call(null) { onResult(it) }
