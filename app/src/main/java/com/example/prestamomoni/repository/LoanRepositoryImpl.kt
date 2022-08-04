package com.example.prestamomoni.repository


import com.example.prestamomoni.data.model.ErrorDTO
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.model.RegistrationResult
import com.example.prestamomoni.data.model.ResultStatus
import com.example.prestamomoni.data.remote.LoanDataSource

class LoanRepositoryImpl(private val dataSource:LoanDataSource): LoanRepository {
    override suspend fun loan(dni: String): ResultStatus =dataSource.loan(dni)

    override suspend fun saveLoan(loan: Loan): ErrorDTO =dataSource.saveLoan(loan)

    override suspend fun getLoanList(): List<Loan>? =dataSource.getLoanList()


    override suspend fun removeLoan(id: String){
        dataSource.removeLoan(id)
    }
}