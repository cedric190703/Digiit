package com.example.digiit.photos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.digiit.R
import com.example.digiit.ticketinfo.DialogTicketInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import coil.compose.rememberImagePainter
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.digiit.data.user.User

fun createBitmapFromUri(context: Context, uri: Uri?): Bitmap {
    val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
    return BitmapFactory.decodeStream(inputStream)
}

fun rotateBitmap(bitmap: Bitmap): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(90f)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

class TakePhoto : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            shouldShowCamera.value = true
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (shouldShowCamera.value) {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = { Log.e("kilo", "View error:", it) }
                )
            }

            if (shouldShowPhoto.value) {
                Image(
                    painter = rememberImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        shouldShowCamera.value = false
        photoUri = uri
        shouldShowPhoto.value = true
        val resultIntent = Intent().apply {
            putExtra("PHOTO_URI", photoUri)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}

@Composable
fun selectOption(setShowDialog: (Boolean) -> Unit, user: User?){
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }
    val showDialogPhoto = remember { mutableStateOf(false) }
    val stateTakePhoto = remember { mutableStateOf(false)}
    val takePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.getParcelableExtra<Uri>("PHOTO_URI")
            if (uri != null) {
                photoUri = uri
                showDialogPhoto.value = true
            }
        }
    }
    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            photoUri = uri
        }
    )
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { setShowDialog(false) }) {
                            Icon(imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(32.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sélectionner une action",
                        fontSize = 27.sp
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(85.dp).fillMaxWidth().padding(horizontal = 5.dp),
                        text = {  Text(text = "Sélectionner une photo",
                            fontSize = 17.sp, fontWeight = FontWeight.Bold
                        ) },
                        onClick = { launcher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                            showDialogPhoto.value = true
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(
                                painterResource(id = R.drawable.select_photo),
                                "Logo select photo",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White)
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(85.dp).fillMaxWidth().padding(horizontal = 5.dp),
                        text = {  Text(text = "Prendre une photo",
                            fontSize = 17.sp) },
                        onClick = {
                            stateTakePhoto.value = true
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(painter = painterResource(id = R.drawable.take_photo),
                                "Logo take photo",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White)
                        }
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    ExtendedFloatingActionButton(
                        modifier = Modifier.height(85.dp).fillMaxWidth().padding(horizontal = 5.dp),
                        text = {  Text(text = "Sélectionner un fichier",
                            fontSize = 17.sp) },
                        onClick = {
                            pickFileLauncher.launch("application/pdf,text/plain,image/jpeg,image/png,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                            showDialogPhoto.value = true
                                  },
                        backgroundColor = MaterialTheme.colors.primary,
                        icon = {
                            Icon(painter = painterResource(id = R.drawable.file),
                                "Logo select a file",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(35.dp),
                                tint = Color.White)
                        }
                    )
                    if (photoUri != null) {
                        val bitmapTmp: Bitmap = createBitmapFromUri(context = LocalContext.current, uri = photoUri)
                        val bitmap = rotateBitmap(bitmapTmp)
                        if(showDialogPhoto.value)
                        {
                            DialogTicketInfo(setShowDialogPhoto = {
                                showDialogPhoto.value = it
                                setShowDialog(false)
                            }, bitmap = bitmap, user = user)
                        }
                    }
                    if(stateTakePhoto.value)
                    {
                        takePhotoLauncher.launch(Intent(LocalContext.current, TakePhoto::class.java))
                        stateTakePhoto.value = false
                    }

                }
            }
        }
    }
}