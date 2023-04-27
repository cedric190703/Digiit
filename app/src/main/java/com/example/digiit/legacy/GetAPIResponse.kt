package com.example.digiit.legacy

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.*
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

data class ApiResponse(
    val status: String,
    val message: String,
    val title: String?,
    val time: String?,
    val date: String?,
    val total: String?
)

fun getApiResponse(imageFile: File, apiUrl: String): ApiResponse {
    val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    try {
        val file = File(imageFile.toURI())

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", imageFile.name, imageFile.asRequestBody("image/jpg".toMediaTypeOrNull()))
            .build()

        val request = Request.Builder()
            .url(apiUrl)
            .method("POST", requestBody)
            .header("Content-Type", "multipart/form-data") // Add this line
            .build()

        Log.d("image", "$file")

        Log.d("request", "$requestBody")

        // Get the response from the API
        val response: Response = client.newCall(request).execute()

        Log.d("response", "$response")

        // Check if the response is not valid
        if (response.code == 400 || !response.isSuccessful) {
            return ApiResponse(
                status = "error",
                message = "Image processing failed",
                title = "",
                time = "",
                date = "",
                total = ""
            )
        }

        // Put the API response into string
        val responseBody = response.body?.string() ?: ""

        val jsonObject = JSONObject(responseBody)
        Log.d("JSON Object from the API", "$jsonObject")

        val title = if (jsonObject.getString("title") == "null") "" else jsonObject.getString("title")
        val time = if (jsonObject.getString("time") == "null") "" else jsonObject.getString("time")
        val date = if (jsonObject.getString("date") == "null") "" else jsonObject.getString("date")
        val total = if (jsonObject.getString("total") == "null") "" else jsonObject.getString("total")

        val apiResponse = ApiResponse(
            status = "success",
            message = "Image processed successfully",
            title = title,
            time = time,
            date = date,
            total = total
        )

        response.close()
        return apiResponse
    } catch (e: Exception) {
        Log.e("getApiResponse", "Error: ${e.message}")
        return ApiResponse(
            status = "error",
            message = "Image processing failed",
            title = "",
            time = "",
            date = "",
            total = ""
        )
    }
}
