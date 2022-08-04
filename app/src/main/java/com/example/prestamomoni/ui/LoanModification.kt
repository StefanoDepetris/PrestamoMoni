package com.example.prestamomoni.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.prestamomoni.R
import com.example.prestamomoni.databinding.FragmentLoanListBinding
import com.example.prestamomoni.databinding.FragmentLoanModificationBinding


class LoanModification : Fragment(R.layout.fragment_loan_modification) {


    private lateinit var binding: FragmentLoanModificationBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val args by navArgs<LoanModificationArgs>()
        val loan=args.loanObj

        Toast.makeText(context, "Hola ${loan.name} ",Toast.LENGTH_SHORT).show()

        binding=FragmentLoanModificationBinding.bind(view)

        val newName = binding.etNombre
        loan.name=newName.text.toString()



        val newLast = binding.etApellido
        loan.last=newLast.text.toString()



        val newEmail = binding.etEmail
        loan.email=newEmail.text.toString()



        val newGenre = binding.etGenero
        loan.genre=newGenre.text.toString()



        val dniOld = binding.etDni
        loan.document=dniOld.text.toString()



        binding.btnModificarSolicitud.setOnClickListener {
            Toast.makeText(context, R.string.proximamente, Toast.LENGTH_LONG).show()
        }

        super.onViewCreated(view, savedInstanceState)
    }


}