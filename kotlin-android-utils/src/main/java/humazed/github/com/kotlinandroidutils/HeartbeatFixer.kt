package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.content.Intent


fun Context.triggerHeartbeat() {
    sendBroadcast(Intent("com.google.android.intent.action.GTALK_HEARTBEAT"))
    sendBroadcast(Intent("com.google.android.intent.action.MCS_HEARTBEAT"))
}