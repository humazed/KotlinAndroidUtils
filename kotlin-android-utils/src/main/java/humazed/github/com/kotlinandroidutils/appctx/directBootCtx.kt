package humazed.github.com.kotlinandroidutils.appctx

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi

/**
 * Lazily creates a device protected storage Context on Android N+ devices,
 * or initializes itself to [appCtx] if the device runs Android M or an older version.
 * See [Direct Boot documentation](https://developer.android.com/training/articles/direct-boot.html)
 * to learn more.
 */
inline val directBootCtx: Context get() = if (SDK_INT < 24) appCtx else deviceProtectedStorageCtx.value

@PublishedApi
@RequiresApi(24)
internal val deviceProtectedStorageCtx = lazy { appCtx.createDeviceProtectedStorageContext() }