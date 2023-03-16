package humazed.github.com.kotlinandroidutils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * create Notification Channel for Android Oreo and above.
 * every options is optional, if you doesn't matter whatever value,
 * leave them no parameters.
 *
 * @param[id] channel id, if this value is not present, it will be package name
 * @param[name] channel name, if this value is not present, it will be app name
 * @param[description] channel description, if this value is not present, it will be app name
 * @param[importance] importance of channel, if this value is not present, it will be IMPORTANCE_HIGH
 * @return generated channel id
 */
@RequiresApi(Build.VERSION_CODES.O)
@JvmOverloads
fun Context.createNotificationChannel(
    id: String? = null,
    name: String? = null,
    description: String? = null,
    importance: Int = NotificationManager.IMPORTANCE_HIGH
): String? {
    if (Build.VERSION.SDK_INT < 26) return null

    val newId = id ?: packageName
    val appName =
        if (applicationInfo.labelRes != 0) getString(applicationInfo.labelRes) else applicationInfo.nonLocalizedLabel.toString()
    val newName = name ?: appName
    val newDescription = description ?: appName

    val mChannel = NotificationChannel(newId, newName, importance)
    mChannel.description = newDescription

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)

    return newId
}