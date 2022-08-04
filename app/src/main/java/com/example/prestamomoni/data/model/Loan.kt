package com.example.prestamomoni.data.model

import java.io.Serializable

data class Loan (

    var name : String? = "",
    var last : String? = "",
    var document : String? = "",
    var email : String? = "",
    var genre : String? = "",
    var loanStatus : String? = "",
    var id : String? = ""
) : Serializable