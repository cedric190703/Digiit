package com.example.digiit.getAPIResponse

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.*
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream

data class ApiResponse(
    val status: String,
    val message: String,
    val title: String?,
    val time: String?,
    val date: String?,
    val total: String?
)

fun getApiResponse(imageFile: File, apiUrl: String): ApiResponse {
    val client = OkHttpClient()
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart(
            "image", imageFile.name,
            RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        )
        .build()
    val request = Request.Builder()
        .url(apiUrl)
        .post(requestBody)
        .build()

    val response: Response = client.newCall(request).execute()
    val responseBody = response.body?.string() ?: ""

    val jsonObject = JSONObject(responseBody)
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
}