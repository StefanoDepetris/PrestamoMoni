package com.example.prestamomoni.data.remote

import com.example.prestamomoni.application.AppContance
import com.example.prestamomoni.data.model.*
import com.example.prestamomoni.repository.WebService
import com.google.gson.Gson

class LoanDataSource(val webService: WebService) {

    //TODO Se lo tengo que pasar como parametro al documento
    suspend fun loan(document: String): ResultStatus =
        webService.loanStatus(document, AppContance.API_KEY)
//    val name : String? = null,
//    val last : String? = null,
//    val document : String? = null,
//    val email : String? = null,
//    val genre : String? = null,
//    var loanStatus : String? = null

    suspend fun saveLoan(loan: Loan): ErrorDTO = webService.saveLoan(loan)

    suspend fun getLoanList(): List<Loan>? {
        webService.getLoanList()?.let {
            val list = it.map { it2 ->
                val jsonTree = Gson().toJsonTree(it2.value) // Transformo un any en un JsonTree
                Gson().fromJson(jsonTree, RequestLoanListResponse::class.java)
            }.toList()

            val listKey = it.map { it2 ->
                val jsonTree = Gson().toJsonTree(it2.key) // Transformo un any en un JsonTree
                Gson().fromJson(jsonTree, String::class.java)
            }.toList()

            val listLoan = list.map { it3 ->
                return@map Loan(
                    it3.nombre,
                    it3.apellido,
                    it3.dni,
                    it3.email,
                    it3.genero,
                    it3.estadoPrestamo

                )
            }.toList()//Single<List<Loan>>


            listLoan.forEachIndexed { index, loan ->
                loan.id=listKey[index]

            }


            return listLoan
        }


        return listOf<Loan>()
    }

    //
//
//
    suspend fun removeLoan(id: String)=webService.removeLoan(id)

}