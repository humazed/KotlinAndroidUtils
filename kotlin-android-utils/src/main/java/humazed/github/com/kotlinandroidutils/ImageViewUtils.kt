package humazed.github.com.kotlinandroidutils

import android.widget.ImageView
import java.io.File

fun ImageView.setImageFile(imageFile: File) {
    setImageURI(imageFile.toUri())
}