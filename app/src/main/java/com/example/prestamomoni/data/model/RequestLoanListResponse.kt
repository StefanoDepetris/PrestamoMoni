package com.example.prestamomoni.data.model

import com.google.gson.annotations.SerializedName

data class RequestLoanListResponse (
    @SerializedName("name")
    val nombre : String? = "",
    @SerializedName("last")
    val apellido : String? = "",
    @SerializedName("document")
    val dni : String? = "",
    @SerializedName("email")
    val email : String? = "",
    @SerializedName("genre")
    val genero : String? = "",
    @SerializedName("loanStatus")
    val estadoPrestamo : String? = ""
)