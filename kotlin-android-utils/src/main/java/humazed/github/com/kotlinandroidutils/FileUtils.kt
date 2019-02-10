package humazed.github.com.kotlinandroidutils

import android.net.Uri
import java.io.File

fun File.toUri(): Uri = Uri.fromFile(this)
