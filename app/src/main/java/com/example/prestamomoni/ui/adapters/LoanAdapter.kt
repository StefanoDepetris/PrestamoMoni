package com.example.prestamomoni.ui.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prestamomoni.R
import com.example.prestamomoni.core.BaseViewHolder
import com.example.prestamomoni.core.Resource
import com.example.prestamomoni.data.model.Loan
import com.example.prestamomoni.data.remote.LoanDataSource
import com.example.prestamomoni.databinding.ItemLoanBinding
import com.example.prestamomoni.presentation.LoanViewModel
import com.example.prestamomoni.presentation.LoanViewModelFactory
import com.example.prestamomoni.repository.LoanRepositoryImpl
import com.example.prestamomoni.repository.RetrofitClient
import kotlinx.coroutines.NonDisposableHandle.parent

class LoanAdapter(
    private val loanList: List<Loan>,
    private val itemClickListener: onMovieClickListener, private val viewModel: LoanViewModel
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    interface onMovieClickListener {
        fun onMovieClick(loan: Loan)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = LoanViewHolder(itemBinding, parent.context)
        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener//Si apreto algun elemento  si es -1 no devuelve nada

            itemClickListener.onMovieClick(loanList[position])
        }
        return holder
    }


    //a cada elemento de la lista neceismos poner este objeto
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is LoanViewHolder -> holder.bind(loanList[position], position)
        }

//        loanList[position].id?.let {
//            viewModel.removeLoan(it).observeForever {
//
//
//                when (it) {
//
//                    is Resource.Loading -> {
//                    }
//
//                    is Resource.Success -> {
//                        if(it.data){
//                            notifyItemRemoved(position)
//                            notifyDataSetChanged()
//                        }
//
//                    }
//
//
//                    is Resource.Failure -> {
//                    }
//                    }
//
//
//                }
////                this as LifecycleOwner, Observer
////                {
////                    println("position ${position}")
////                    notifyItemRemoved(position)
////
////                })
//            }


    }

    override fun getItemCount(): Int = loanList.size


    //Al se inner forma parte de movieAdapter cuando instance MovieAdapter si instancia esta por eso el inner.
    // El inner lo que hace es que cuando muera MovieAapter(el objeto muere este tambien)

    //Binding.root es toda layout completa es decir todo  Movieitem

    //Viewholder se crea apartior de la vista itemBinding, este BaseViewHolder tiene una movie.
    // Y le pasamos una vista para poder bindearle la pelicula.
    private inner class LoanViewHolder(val binding: ItemLoanBinding, val context: Context) :
        BaseViewHolder<Loan>(binding.root) {
        override fun bind(item: Loan, position: Int) {
            val res: Resources = this.itemView.resources

            binding.tvItemDni.text = res.getString(R.string.item_titular_dni, item.document)
            binding.tvItemGender.text = res.getString(R.string.item_titular_genero, item.genre)
            binding.tvItemNamePerson.text =
                res.getString(R.string.item_titular_nombre, item.name, item.last)
            binding.tvItemEmail.text = res.getString(R.string.item_titular_email, item.email)
            binding.tvItemStatusLoan.text = res.getString(R.string.item_status, item.loanStatus)

            binding.btnRemove.setOnClickListener {
                item.id?.let { it1 ->

                    viewModel.removeLoan(it1)
                        .observe( itemView.context as LifecycleOwner, Observer { result ->
                            when (result) {

                                is Resource.Loading -> {

                                }

                                is Resource.Success -> {
                                    if (result.data) {
                                        (loanList as ArrayList<Loan>).remove(item)
                                        notifyItemRemoved(position)
                                        notifyDataSetChanged()
                                    }

                                }
                                is Resource.Failure -> {
                                    Log.d("Erorr", "${result.exception}")

                                }

                            }
                        })

                }
            }


        }
    }
}