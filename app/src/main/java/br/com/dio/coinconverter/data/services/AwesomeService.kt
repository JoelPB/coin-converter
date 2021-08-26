package br.com.dio.coinconverter.data.services

import br.com.dio.coinconverter.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Part

interface AwesomeService {

    @GET("/json/last/{coins}")
    suspend fun exchangeValue(@Part("coins") coins: String): ExchangeResponse
}