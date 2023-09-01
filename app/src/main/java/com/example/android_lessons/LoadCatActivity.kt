package com.example.android_lessons

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

class LoadCatActivity : AppCompatActivity() {

    @Serializable
    data class CatResponse (
        val id: String,
        val url: String,
        val width: Int,
        val height: Int
    )

    interface CatApiService {
        @GET("images/search")
        suspend fun getCats(@Query("size") size: String) : List<CatResponse>
    }

    companion object {
        private const val URL = "https://api.thecatapi.com/v1/images/search?size=small"
        private const val BASE_URL = "https://api.thecatapi.com/v1/"
    }

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttp
    private val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val request = Request.Builder()
        .url(URL)
        .build()
    // Retrofit
    private val scope = CoroutineScope(Dispatchers.IO)
    private val contentType = "application/json".toMediaType()
    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(client)
        .build()
    private val catApiService: CatApiService = retrofitClient.create(CatApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_cat)
        val catImage = findViewById<ImageView>(R.id.img_cat)
        findViewById<Button>(R.id.btn_load_cat)?.apply {
            setOnClickListener {
                loadCat()
            }
        }
        findViewById<Button>(R.id.btn_load_cat_retro)?.apply {
            setOnClickListener {
                scope.launch {
                    val cat: List<CatResponse> = catApiService.getCats("small")
//                    Log.d("tag", cat.joinToString())
                    runOnUiThread {
                        catImage.load(cat[0].url) {
                            crossfade(true)
                            placeholder(R.drawable.ic_launcher_foreground)
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun loadCat() {
        val catText = findViewById<TextView>(R.id.text_cat)

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Запрос к серверу не был успешен: ${response.code} ${response.message}")
                    }
                    val content = response.body?.string() ?: return
                    val catResponse = json.decodeFromString<List<CatResponse>>(content)
//                    Log.d("tag", catResponse.joinToString())
//                    runOnUiThread {
//                        catText.text = catResponse[0].id
//                    }
                    loadCatImage(catResponse[0].url)
                }
            }
        })
    }

    private fun loadCatImage(url: String) {
        val imageRequest = Request.Builder()
            .url(url)
            .build()
        client.newCall(imageRequest).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    throw IOException("Запрос к серверу не был успешен: ${response.code} ${response.message}")
                }
                val inputStream = response.body?.byteStream() ?: return
                val bitmap = BitmapFactory.decodeStream(inputStream)
                runOnUiThread {
                    findViewById<ImageView>(R.id.img_cat)?.apply {
                        setImageBitmap(bitmap)
                    }
                }
            }
        })
    }
}