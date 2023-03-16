package humazed.github.com.kotlinandroidutils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.*
import androidx.core.content.FileProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import stream.customimagepicker.CustomImagePicker
import stream.jess.ui.TwoWayGridView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// added to cash the bottomSheet as it was crashing the app when opening multiple times
private val Activity.bottomSheetPicker: Pair<View, Dialog> by LazyWithReceiver<Activity, Pair<View, Dialog>> {
    //Initialize Image Picker Dialogue Popup.
    val bottomSheet = layoutInflater.inflate(R.layout.bottom_sheet, null)
    val bottomSheetDialog = Dialog(this, R.style.MaterialDialogSheet).apply {
        setContentView(bottomSheet)
        setCancelable(true)
        window?.setLayout(MATCH_PARENT, WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
    }

    Pair(bottomSheet, bottomSheetDialog)
}

fun Activity.pickImage(
    onCancelOrFail: () -> Unit = {},
    onItemSelected: (imageFile: File, uri: Uri) -> Unit,
) {
    val (bottomSheet: View, bottomSheetDialog: Dialog) = bottomSheetPicker

    var dismissedForGalleryOrCamera = false
    var pickedFile: File? = null

    bottomSheetDialog.setOnDismissListener {
        if (!dismissedForGalleryOrCamera && pickedFile == null) onCancelOrFail()
    }

    //Initialize Image Picker Menu Actions.
    val layoutCamera = bottomSheet.findViewById<LinearLayout>(R.id.btn_camera)
    val layoutGallery = bottomSheet.findViewById<LinearLayout>(R.id.btn_gallery)
    layoutCamera.setOnClickListener {
        dispatchTakePicture(
            onSuccess = { imageFile ->
                pickedFile = imageFile
                val uri: Uri =
                    FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
                onItemSelected(imageFile, uri)
            },
            onCancelOrFail = onCancelOrFail,
        )
        dismissedForGalleryOrCamera = true
        bottomSheetDialog.dismiss()
    }

    layoutGallery.setOnClickListener {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { imageUri ->
                    imageUri.getFilePath(applicationContext)?.let {
                        val file = File(it)
                        pickedFile = file
                        onItemSelected(file, file.toUri())
                    }
                }
            } else {
                onCancelOrFail()
            }
        }
        dismissedForGalleryOrCamera = true
        bottomSheetDialog.dismiss()
    }

    val imagePicker = CustomImagePicker().apply {
        setHeight(100)
        setWidth(100)
    }
    val adapter = imagePicker.getAdapter(this)

    bottomSheet.findViewById<TwoWayGridView>(R.id.gridview).apply {
        layoutParams.height = dpToPx(200)
        setNumRows(2)
        setAdapter(adapter)

        setOnItemClickListener { _, _, _, id ->
            val imageUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            imageUri.getFilePath(applicationContext)?.let {
                val file = File(it)
                pickedFile = file
                onItemSelected(file, file.toUri())
            }
            bottomSheetDialog.dismiss()
        }
    }
    bottomSheetDialog.show()
}

fun Activity.pickImageWithPermission(
    onCancelOrFail: () -> Unit = {},
    onItemSelected: (imageFile: File, uri: Uri) -> Unit,
) {
    val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            pickImage(onCancelOrFail, onItemSelected)
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>) {
            longToast("Permission Denied\n$deniedPermissions")
        }
    }
    TedPermission.create()
        .setPermissionListener(permissionListener)
        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        .check()
}

private fun Context.dispatchTakePicture(
    onSuccess: (imageFile: File) -> Unit,
    onCancelOrFail: () -> Unit,
) {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    // Ensure that there's a camera activity to handle the intent
    takePictureIntent.resolveActivity(packageManager)?.also {
        // Create the File where the photo should go
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            er { ex }
            // Error occurred while creating the File
            null
        }
        // Continue only if the File was successfully created
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", it)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(takePictureIntent) { resultCode, data: Intent? ->
                if (resultCode == Activity.RESULT_OK) {
                    galleryAddPic(photoFile)
                    onSuccess(photoFile)
                } else {
                    onCancelOrFail()
                }
            }
        }
    }
}


@Throws(IOException::class)
private fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

private fun Context.galleryAddPic(imageFile: File) {
    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
        mediaScanIntent.data = Uri.fromFile(imageFile)
        sendBroadcast(mediaScanIntent)
    }
}
