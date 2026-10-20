package com.creator_surge.network

import com.creator_surge.model.CreationResult
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

object ApiClient {
    // Render service URL. Change this if the service name changes.
    const val BASE_URL = "https://creator-surge-api.onrender.com"
    private val client = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun create(prompt: String): CreationResult = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val body = JSONObject().put("prompt", prompt).put("mediums", JSONArray(listOf("image", "video", "game", "code", "docs"))).toString()
        val request = Request.Builder().url("$BASE_URL/create").post(body.toRequestBody("application/json".toMediaType())).build()
        client.newCall(request).execute().use { response ->
            val raw = response.body?.string().orEmpty()
            check(response.isSuccessful) { "API error ${response.code}: $raw" }
            json.decodeFromString<CreationResult>(raw)
        }
    }
}
