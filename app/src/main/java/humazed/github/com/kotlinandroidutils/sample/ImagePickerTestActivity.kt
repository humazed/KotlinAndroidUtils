package humazed.github.com.kotlinandroidutils.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import humazed.github.com.kotlinandroidutils.d
import humazed.github.com.kotlinandroidutils.load
import humazed.github.com.kotlinandroidutils.pickImageWithPermission
import kotlinx.android.synthetic.main.activity_image_picker_test.*

class ImagePickerTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker_test)

        pickImageBt.setOnClickListener {
            pickImageWithPermission(
                    onItemSelected = { imageFile, uri ->
                        imageView.load(uri){
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
