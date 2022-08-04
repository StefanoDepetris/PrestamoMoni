package com.example.prestamomoni.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.prestamomoni.R
import com.example.prestamomoni.core.Resource
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.remote.LoanDataSource
import com.example.prestamomoni.databinding.FragmentLoanListBinding
import com.example.prestamomoni.presentation.LoanViewModel
import com.example.prestamomoni.presentation.LoanViewModelFactory
import com.example.prestamomoni.repository.LoanRepositoryImpl
import com.example.prestamomoni.repository.RetrofitClient
import com.example.prestamomoni.ui.adapters.LoanAdapter


class LoanList : Fragment(R.layout.fragment_loan_list), LoanAdapter.onMovieClickListener {

    private val viewModel by viewModels<LoanViewModel> {

        //Inyeccion de depencias manual cuando creo la instancia de ViewModel
        LoanViewModelFactory(LoanRepositoryImpl(LoanDataSource(RetrofitClient.webservice)))
//        LoanViewModelFactory(LoanRepositoryImpl(LoanDataSource(RetrofitClient2.webservice)))
    }


    private lateinit var binding: FragmentLoanListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding = FragmentLoanListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val concatAdapter = ConcatAdapter()






        viewModel.fetchLoanList().observe(
            viewLifecycleOwner, Observer
            { result ->
                when (result) {

                    is Resource.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE

                    }

                    is Resource.Success -> {

                        println("Entro aca aaaaaa")
                        binding.progressbar.visibility = View.GONE
                        Log.d("List loan ", "${result.data}")
                        binding.rcvLoanList.adapter=
                            result.data?.let {
                                LoanAdapter(it,this@LoanList,viewModel)
                            }






                    }
                    is Resource.Failure -> {
                        Log.d("Erorr", "${result.exception}")

                    }

                }

            })



    }

    override fun onMovieClick(loan: Loan) {

        val action = LoanListDirections.actionLoanListToLoanModification(loan)
        findNavController().navigate(action)


    }
}