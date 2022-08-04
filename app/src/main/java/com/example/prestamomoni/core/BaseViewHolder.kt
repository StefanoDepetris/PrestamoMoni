package com.example.prestamomoni.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition

//Facilita la creacion de adapters
abstract class BaseViewHolder<T>(itemview: View): RecyclerView.ViewHolder(itemview) {

    //Bindea cada uno de los elementos itemView y ponerle los datos que reiquiere
    abstract  fun bind(item: T,position: Int)
}