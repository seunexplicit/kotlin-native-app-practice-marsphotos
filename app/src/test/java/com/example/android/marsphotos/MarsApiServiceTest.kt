package com.example.android.marsphotos

import com.example.android.marsphotos.network.MarsApiService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MarsApiServiceTest:BaseTest() {

    private lateinit var marsApiService: MarsApiService

    @Before
    fun setup(){
        var url = mockWebServer.url("/")

        marsApiService = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(MarsApiService::class.java)
    }

    @Test
    fun api_service(){
        enqueue("mars_photos.json")
        runBlocking {
            val apiResponse = marsApiService.getPhotos()
            assertNotNull(apiResponse)
            assertTrue("This list was empty", apiResponse.isNotEmpty())
            assertEquals("This id's did not match", "424905", apiResponse[0].id)
        }
    }
}