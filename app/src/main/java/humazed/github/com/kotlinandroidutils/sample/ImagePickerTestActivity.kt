package humazed.github.com.kotlinandroidutils.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import humazed.github.com.kotlinandroidutils.d
import humazed.github.com.kotlinandroidutils.load
import humazed.github.com.kotlinandroidutils.pickImageWithPermission
import humazed.github.com.kotlinandroidutils.sample.databinding.ActivityImagePickerTestBinding

class ImagePickerTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePickerTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePickerTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.pickImageBt.setOnClickListener {
            pickImageWithPermission(
                    onItemSelected = { imageFile, uri ->
                        binding.imageView.load(uri){
                            placeholder(0)
                        }
                    },
                    onCancelOrFail = {
                        d { "onCancelOrFail " }
                    },
            )
//            pickImageWithPermission { imageFile, uri ->
//
//            }
        }
    }
}
