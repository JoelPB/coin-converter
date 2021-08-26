package br.com.dio.coinconverter.data.services

import retrofit2.http.GET
import retrofit2.http.Part

interface AwesomeService {

    @GET("/json/last/{coins}")
    suspend fun exchangeValues(@Part("coins") coins: String)
}