package com.example.prestamomoni.repository

import com.example.prestamomoni.application.AppContance
import com.example.prestamomoni.data.model.ErrorDTO
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.model.RegistrationResult
import com.example.prestamomoni.data.model.ResultStatus
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface WebService {

    @GET("{dni}")
    suspend fun loanStatus(@Path("dni") dni : String, @Header("credential") header :String) : ResultStatus



    @POST("https://wired-torus-98413.firebaseio.com/users.json")
    suspend fun saveLoan(@Body loanAplicationRequest : Loan) : ErrorDTO

//
    @DELETE("https://wired-torus-98413.firebaseio.com/users/{id}.json")
    suspend fun removeLoan(@Path("id")id: String)
//

    @GET("https://wired-torus-98413.firebaseio.com/users.json")
    suspend fun getLoanList() : Map<String,Any>?

}

object RetrofitClient{
    //el by lazy cuando llamemos retofitClient.webservice se va inicilizar cunado se llame no antes
    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl(AppContance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}