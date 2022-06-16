package ru.beryukhov.retrofitcoroutinesexample

import retrofit2.http.GET
import retrofit2.http.Query

interface TrainsInterface {

    @GET("search")
    suspend fun getElektrichki(
        @Query("apikey") api_key: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("date") date: String,
        @Query("transport_types") transport_types: String,
        @Query("limit") limit: Int
    ): TrainModel

}
