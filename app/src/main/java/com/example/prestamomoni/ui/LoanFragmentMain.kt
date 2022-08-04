package com.example.prestamomoni.ui

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.prestamomoni.R
import com.example.prestamomoni.core.Resource
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.remote.LoanDataSource
import com.example.prestamomoni.databinding.FragmentLoanMainBinding
import com.example.prestamomoni.presentation.LoanViewModel
import com.example.prestamomoni.presentation.LoanViewModelFactory
import com.example.prestamomoni.repository.LoanRepositoryImpl
import com.example.prestamomoni.repository.RetrofitClient


class LoanFragmentMain : Fragment(R.layout.fragment_loan_main) {


    private val viewModel by viewModels<LoanViewModel> {

        //Inyeccion de depencias manual cuando creo la instancia de ViewModel
        LoanViewModelFactory(LoanRepositoryImpl(LoanDataSource(RetrofitClient.webservice)))
//        LoanViewModelFactory(LoanRepositoryImpl(LoanDataSource(RetrofitClient2.webservice)))
    }

    private lateinit var binding: FragmentLoanMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        binding = FragmentLoanMainBinding.bind(view)

        val name: EditText = binding.nombre
        val lastName: EditText = binding.apellido
        val email: EditText = binding.email
        val genero: EditText = binding.genero
        val dni: EditText = binding.dni


        binding.button.setOnClickListener {

            viewModel.fetchStatusLoan(name.text.toString(),lastName.text.toString(),dni.text.toString(),genero.text.toString(),email.text.toString())
                .observe(viewLifecycleOwner, Observer { result ->
                    when (result) {

                        is Resource.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                            binding.textView.visibility = View.GONE
                            Log.d("Livedata upcoming", "Loaging...")
                        }

                        is Resource.Success -> {
                            binding.progressbar.visibility = View.GONE
                            Log.d("Livedata ", "${result.data}")

                            binding.textView.visibility = View.VISIBLE
                            if (result.data.isError == false){
                                binding.textView.text = "Estado del prestamos: ${result.data.status}"
                                viewModel.fetchSaveLoan(Loan(name.text.toString(),lastName.text.toString(),dni.text.toString(),email.text.toString(), genero.text.toString(),result.data.status)).observe(viewLifecycleOwner,
                                    Observer { resultSave->
                                        Toast.makeText(context,"Se guardo: ${resultSave.error}",Toast.LENGTH_LONG).show()
                                    })
                            }

                            else
                                binding.textView.text ="Intente mas tarde"


                        }
                        is Resource.Failure -> {
                            Log.d("Erorr", "${result.exception}")

                        }

                    }
                })
        }

        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loanList)
        }

    }
}