package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_image_full_screen.*

const val KEY_FULL_SCREEN_IMAGE_URI = "ImageFullScreenActivity:KEY_IMAGE_URI"
const val KEY_FULL_SCREEN_IMAGE_URL = "ImageFullScreenActivity:KEY_IMAGE_URL"

class ImageFullScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full_screen)

        photoView.setImageURI(intent.getParcelableExtra(KEY_FULL_SCREEN_IMAGE_URI))
        photoView.load(intent.getStringExtra(KEY_FULL_SCREEN_IMAGE_URL))


        closeButton.setOnClickListener {
            finish()
        }
    }

}


fun Context.openImageFullScreen(imageUri: Uri) {
    start<ImageFullScreenActivity>(KEY_FULL_SCREEN_IMAGE_URI to imageUri)
}

fun Fragment.openImageFullScreen(imageUri: Uri) = context?.openImageFullScreen(imageUri)


fun Context.openImageFullScreen(imageUrl: String) {
    start<ImageFullScreenActivity>(KEY_FULL_SCREEN_IMAGE_URL to imageUrl)
}

fun Fragment.openImageFullScreen(imageUrl: String) = context?.openImageFullScreen(imageUrl)

