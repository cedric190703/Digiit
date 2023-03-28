package com.example.digiit.OCR

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.*
import java.io.File
import java.io.FileInputStream

data class ApiResponse(val status: String, val message: String)

// Function to send an image to the Python Flask server and receive the JSON response
fun getApiResponse(imageFile: File, apiUrl: String): ApiResponse {
    // Read the image file into a ByteArray using FileInputStream
    val imageBytes = FileInputStream(imageFile).use { input ->
        input.readBytes()
    }

    // Create a RequestBody with the image bytes as the request body
    val requestBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), imageBytes)

    // Create a POST request with the RequestBody and the API URL
    val request = Request.Builder()
        .url(apiUrl)
        .post(requestBody)
        .build()

    // Create an OkHttpClient and execute the request
    val client = OkHttpClient()
    val response: Response = client.newCall(request).execute()

    // Parse the JSON response into an ApiResponse object using Gson
    val gson = Gson()
    val responseBody = response.body?.string() ?: ""
    val apiResponse = gson.fromJson(responseBody, ApiResponse::class.java)

    // Close the response and return the ApiResponse object
    response.close()
    return apiResponse
}
