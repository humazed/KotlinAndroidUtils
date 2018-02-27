package humazed.github.com.kotlinandroidutils

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log

/**
 * Check if the service is running in the device.
 *
 * @param serviceClass the service class
 * @return whether the service is running
 */
fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    try {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) return true
        }
    } catch (e: Exception) {
        Log.e("ServiceUtils", Log.getStackTraceString(e))
    }
    return false
}

inline fun <reified T : Service> Context.isServiceRunning() = isServiceRunning(T::class.java)