package humazed.github.com.kotlinandroidutils

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import humazed.github.com.kotlinandroidutils.databinding.ActivityImageFullScreenBinding

const val KEY_FULL_SCREEN_IMAGE_URI = "ImageFullScreenActivity:KEY_IMAGE_URI"
const val KEY_FULL_SCREEN_IMAGE_URL = "ImageFullScreenActivity:KEY_IMAGE_URL"

class ImageFullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageFullScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.photoView.setImageURI(intent.getParcelableExtra(KEY_FULL_SCREEN_IMAGE_URI))
        binding.photoView.load(intent.getStringExtra(KEY_FULL_SCREEN_IMAGE_URL))


        binding.closeButton.setOnClickListener {
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

