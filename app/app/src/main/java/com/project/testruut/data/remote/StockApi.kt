package com.project.testruut.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {
    @GET("query?function=LISTING_STATUS")
    suspend fun getListStrings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val API_KEY = "32G9LZN47CGML80L"
        const val BASE_URL = "https://alphavantage.co"
    }

}