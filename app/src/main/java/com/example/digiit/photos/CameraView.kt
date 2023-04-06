package com.example.digiit.photos

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.digiit.BarCodeScanner.BarcodeAnalyser
import com.example.digiit.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@ExperimentalGetImage
@Composable
fun CameraView(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    mode: CameraMode,
    stopActivity: () -> Unit
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val imageAnalyzer = if (mode == CameraMode.SCANNER) {
        ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(executor, BarcodeAnalyser {barcodeValue ->
                    // Use the barcodevalue here
                    // TODO
                    Toast.makeText(context, "Le code bar est trouvé", Toast.LENGTH_SHORT).show()
                })
            }
    } else null

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    val flashMode = remember { mutableStateOf(ImageCapture.FLASH_MODE_OFF) }

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        if (imageAnalyzer != null) {
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalyzer
            )
        } else {
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        }

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        if (mode == CameraMode.CAMERA) {
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 75.dp)
                    .align(Alignment.BottomCenter),

                ) {
                IconButton(
                    onClick = {
                        takePhoto(
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            imageCapture = imageCapture,
                            outputDirectory = outputDirectory,
                            executor = executor,
                            onImageCaptured = onImageCaptured,
                            onError = onError,
                            flashMode = flashMode.value
                        )
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Take picture",
                            tint = Color.White,
                            modifier = Modifier
                                .size(90.dp)
                                .padding(1.dp)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                )

                IconToggleButton(
                    checked = flashMode.value == ImageCapture.FLASH_MODE_ON,
                    onCheckedChange = { isChecked ->
                        flashMode.value =
                            if (isChecked) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
                        imageCapture.flashMode = flashMode.value
                    },
                    modifier = Modifier.padding(start = 20.dp),
                    content = {
                        Icon(
                            painter = if (flashMode.value == ImageCapture.FLASH_MODE_ON) painterResource(
                                id = R.drawable.flash_on
                            ) else painterResource(
                                id = R.drawable.flash_off
                            ),
                            contentDescription = "Flash",
                            tint = if (flashMode.value == ImageCapture.FLASH_MODE_ON) Color.Yellow else Color.White,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(1.dp)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.BottomCenter),
            ) {
                IconButton(
                    onClick = { stopActivity() },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = "Stop",
                            tint = Color.White,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(1.dp)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                )
            }
        }
    }
}

enum class CameraMode {
    CAMERA,
    SCANNER
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    flashMode: Int,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.flashMode = flashMode // set flash mode
    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}