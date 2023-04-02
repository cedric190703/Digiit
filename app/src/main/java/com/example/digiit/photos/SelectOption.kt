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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import com.example.digiit.cards.EditCard
import com.example.digiit.data.card.Card
import com.example.digiit.data.user.User
import com.example.digiit.getAPIResponse.ApiResponse
import com.example.digiit.getAPIResponse.getApiResponse
import com.example.digiit.utils.LottieLoadingAnimation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

fun createBitmapFromUri(context: Context, uri: Uri?): Bitmap {
    val inputStream = uri?.let { context.contentResolver.openInputStream(it) }
    return BitmapFactory.decodeStream(inputStream)
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

enum class TypeScreen {
    Home, Wallet
}

@Composable
fun SelectOption(setShowDialog: (Boolean) -> Unit,
                 user: User?, typeScreen: TypeScreen){
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        photoUri = uri
    }
    val showDialogPhoto = remember { mutableStateOf(false) }
    val stateTakePhoto = remember { mutableStateOf(false)}
    val OCRshowDialog = remember { mutableStateOf(false) }
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
            color = MaterialTheme.colors.background,
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
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
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
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
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
                        modifier = Modifier
                            .height(85.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        text = {  Text(text = "Sélectionner un fichier",
                            fontSize = 17.sp) },
                        onClick = {
                            pickFileLauncher.launch("application/pdf,image/jpeg,image/png")
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
                    if(typeScreen == TypeScreen.Home) {
                        Spacer(modifier = Modifier.padding(12.dp))
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .height(85.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            text = {  Text(text = "Détection du ticket",
                                fontSize = 17.sp) },
                            onClick = {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
                                showDialogPhoto.value = true
                                OCRshowDialog.value = true
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            icon = {
                                Icon(painter = painterResource(id = R.drawable.ocr_scanner),
                                    "Logo select a file",
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .size(35.dp),
                                    tint = Color.White)
                            }
                        )
                    }

                    // Define a boolean state to control the visibility of the dialog
                    var showDialogLoader = remember { mutableStateOf(false) }

                    // Show the dialog when `showDialog` is true
                    if (showDialogLoader.value) {
                        AlertDialog(
                            onDismissRequest = { },
                            title = {
                                Text(
                                    text = "Loading")
                            },
                            text = {
                                LottieLoadingAnimation()
                            },
                            buttons = {}
                        )
                    }

                    if (photoUri != null) {
                        if (showDialogPhoto.value) {
                            val bitmap: Bitmap = createBitmapFromUri(context = LocalContext.current, uri = photoUri)
                            val callFunc = remember { mutableStateOf(false)}
                            val card: Card = if (typeScreen == TypeScreen.Wallet) user!!.createWallet() else user!!.createTicket()
                            card.image = bitmap

                            if (OCRshowDialog.value) {
                                val context = LocalContext.current

                                // Convert bitmap to byte array
                                val outputStream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                                val byteArray = outputStream.toByteArray()

                                // Create file from byte array
                                val file = File(context.cacheDir, "image.jpg")
                                val fileOutputStream = FileOutputStream(file)
                                fileOutputStream.write(byteArray)
                                fileOutputStream.close()

                                // Close the stream
                                outputStream.close()
                                val apiUrl = "https://ocr-3szz.onrender.com/"

                                // Store the API response in a State variable
                                var apiResponse by remember { mutableStateOf<ApiResponse?>(null) }

                                // Use LaunchedEffect to call getApiResponse asynchronously
                                LaunchedEffect(apiUrl, file) {
                                    showDialogLoader.value = true
                                    try {
                                        apiResponse = withContext(Dispatchers.Default) {
                                            withTimeoutOrNull(60000L) {
                                                getApiResponse(imageFile = file, apiUrl = apiUrl)
                                            } ?: ApiResponse(
                                                status = "error",
                                                message = "Timeout reached",
                                                title = "",
                                                time = "",
                                                date = "",
                                                total = ""
                                            )
                                        }

                                    } catch (e: Exception) {
                                        Log.d("Error", "API error")
                                    } finally {
                                        showDialogLoader.value = false
                                        OCRshowDialog.value = false
                                        // Call EditCard when apiResponse is not null
                                        apiResponse?.let { apiResponse ->
                                            if(apiResponse.status == "success") {
                                                Log.d("results", "${apiResponse.title} | ${apiResponse.total}")
                                                card.title = apiResponse.title.orEmpty()
                                                card.price = apiResponse.total?.toFloat() ?: 0f
                                                callFunc.value = true
                                            } else {
                                                card.title = ""
                                                card.price = 0f
                                            }
                                        }
                                    }
                                }
                                if(callFunc.value) {
                                    EditCard(card, {
                                        showDialogPhoto.value = it
                                        setShowDialog(false)
                                    }, {
                                        showDialogPhoto.value = it
                                        setShowDialog(false)
                                    }, false)
                                }
                            } else {
                                EditCard(card, {
                                    showDialogPhoto.value = it
                                    setShowDialog(false)
                                }, {
                                    showDialogPhoto.value = it
                                    setShowDialog(false)
                                }, false)
                            }
                        }
                    }

                    if (stateTakePhoto.value) {
                        takePhotoLauncher.launch(
                            Intent(
                                LocalContext.current,
                                TakePhoto::class.java
                            )
                        )
                        stateTakePhoto.value = false
                    }
                }
            }
        }
    }
}