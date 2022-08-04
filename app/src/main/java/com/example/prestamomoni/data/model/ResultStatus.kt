package com.example.prestamomoni.data.model

import com.google.gson.annotations.SerializedName


data class ResultStatus(
    val status : String? = null,
    @SerializedName("has_error")
    val isError : Boolean? = null
)

