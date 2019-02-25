package humazed.github.com.kotlinandroidutils

import android.content.ContentProvider
import android.content.ContentValues
import android.net.Uri

/**
 * Base class for [ContentProvider]s used for initialization purposes.
 */
abstract class InitProvider : ContentProvider() {
    final override fun insert(uri: Uri, values: ContentValues?) = throw UnsupportedOperationException()
    final override fun query(
            uri: Uri,
            projection: Array<out String>?,
            selection: String?,
            selectionArgs: Array<out String>?,
            sortOrder: String?
    ) = throw UnsupportedOperationException()

    final override fun update(
            uri: Uri,
            values: ContentValues?,
            selection: String?,
            selectionArgs: Array<out String>?
    ) = throw UnsupportedOperationException()

    final override fun delete(
            uri: Uri,
            selection: String?,
            selectionArgs: Array<out String>?
    ) = throw UnsupportedOperationException()

    final override fun getType(uri: Uri) = throw UnsupportedOperationException()
}

