package humazed.github.com.kotlinandroidutils

import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Intent
import android.provider.MediaStore
import android.view.Gravity
import android.widget.LinearLayout
import stream.customimagepicker.CustomImagePicker
import stream.jess.ui.TwoWayGridView
import java.io.File

private const val CAPTURE_IMAGE = 0
private const val SELECT_PHOTO = 1

/**
 *  @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
 */
fun Activity.pickImage(onItemSelected: (imageFile: File) -> Unit) {
    d { "initImagePickerDialog() called with:" }
    //Initialize Image Picker Dialogue Popup.
    val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)
    val bottomSheetDialog = Dialog(this, R.style.MaterialDialogSheet)
    bottomSheetDialog.setContentView(bottomSheet)
    bottomSheetDialog.setCancelable(true)
    bottomSheetDialog.window?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    bottomSheetDialog.window?.setGravity(Gravity.BOTTOM)

    //Initialize Image Picker Menu Actions.
    val layoutCamera = bottomSheet.findViewById<LinearLayout>(R.id.btn_camera)
    val layoutGallery = bottomSheet.findViewById<LinearLayout>(R.id.btn_gallery)
    layoutCamera.setOnClickListener {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAPTURE_IMAGE)
        bottomSheetDialog.dismiss()
    }
    layoutGallery.setOnClickListener {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, SELECT_PHOTO)
        bottomSheetDialog.dismiss()
    }

    val imagePicker = CustomImagePicker()
    imagePicker.setHeight(100)
    imagePicker.setWidth(100)
    val adapter = imagePicker.getAdapter(this)

    val gridView = bottomSheet.findViewById<TwoWayGridView>(R.id.gridview)
    gridView.layoutParams.height = dpToPx(200)
    gridView.setNumRows(2)
    gridView.adapter = adapter

    gridView.setOnItemClickListener { _, _, _, id ->
        val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

        getFilePath(imageUri)?.let { onItemSelected(File(it)) }
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.show()
}

fun Activity.imagePickerOnActivityResult(requestCode: Int, resultCode: Int, data: Intent): File {
    val imageUri = when (requestCode) {
        CAPTURE_IMAGE -> if (resultCode == Activity.RESULT_OK) data.data else null
        SELECT_PHOTO -> if (resultCode == Activity.RESULT_OK) data.data else null
        else -> null
    }

    val imageFilePath = imageUri?.let { getFilePath(it) }
    return File(imageFilePath)
}

