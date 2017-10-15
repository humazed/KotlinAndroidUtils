package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.ConnectivityManager

/**
 * User: YourPc
 * Date: 9/21/2017
 */
fun Context.isConnected(): Boolean {
    val info = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
    return info != null && info.isConnected
}
