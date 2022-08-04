package com.example.prestamomoni.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.prestamomoni.core.Resource
import com.example.prestamomoni.data.model.ErrorDTO
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.model.LoanStatus
import com.example.prestamomoni.data.model.ResultStatus
import com.example.prestamomoni.repository.LoanRepository
import kotlinx.coroutines.Dispatchers

class LoanViewModel(private val repo:LoanRepository): ViewModel() {



    fun fetchStatusLoan(name:String,lastName:String,document:String,gender:String,email:String) = liveData(Dispatchers.IO){
        //Tenemos 3 tipos de estado:Cargando, exito o estado de fallo.

        //Antes de buscar la informacion al servidor tendriamos que decir que esta en modo carganddo la vista
        emit(Resource.Loading())

        try {
            //Con triple devolvemos 3 llamadas, pero si queremos 4 tendriamos qeu crear un data class que aparecen como NTuple4
            emit(Resource.Success(repo.loan(document)))

        }
        catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    fun removeLoan(id: String)= liveData(Dispatchers.IO){
        emit(Resource.Loading())

        try {
            repo.removeLoan(id)
            emit(Resource.Success(true))
        }
        catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    fun fetchSaveLoan(loan:Loan) = liveData<ErrorDTO>(Dispatchers.IO){
        repo.saveLoan(loan)
    }


    fun fetchLoanList()= liveData(Dispatchers.IO){
        emit(Resource.Loading())

        try {
            //Con triple devolvemos 3 llamadas, pero si queremos 4 tendriamos qeu crear un data class que aparecen como NTuple4
            emit(Resource.Success(repo.getLoanList()))

        }
        catch (e:Exception){
            emit(Resource.Failure(e))
        }

    }




}

//Las instancias de viewmodel deben ser vacias pero tenemos la posibilidad de usar factory.
class LoanViewModelFactory(private val repo:LoanRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //Constructor de MovieRpository.
        return modelClass.getConstructor(LoanRepository::class.java).newInstance(repo)
    }

}