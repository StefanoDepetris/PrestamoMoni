package com.example.prestamomoni.repository


import com.example.prestamomoni.data.model.ErrorDTO
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.model.RegistrationResult
import com.example.prestamomoni.data.model.ResultStatus

interface LoanRepository {
    suspend fun loan(dni :String) : ResultStatus
    suspend fun saveLoan(loan:Loan): ErrorDTO
    suspend fun getLoanList():List<Loan>?

    suspend fun removeLoan(id :String)

}