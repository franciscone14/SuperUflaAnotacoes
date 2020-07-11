package com.ufla.superuflaanotaes.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.ufla.superuflaanotaes.R
import com.ufla.superuflaanotaes.models.Nota

class NotaAdapter (var context: Context, var arrayList: ArrayList<Nota>) :BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.card_anotacao, null)


        var id:TextView = view.findViewById(R.id.textId)
        var titulo:TextView = view.findViewById(R.id.textTitulo)
//        var dataCriacao:TextView = view.findViewById(R.id.dataCriacaoText)
        var resumo:TextView = view.findViewById(R.id.textResumo)

        var nota:Nota = arrayList.get(position)
        id.text = "#" + nota.id?.toString()
        titulo.text = nota.titulo
//        dataCriacao.text = nota.dataDeCadastro.toString()
        resumo.text = nota.resumo

        return view!!
    }

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return arrayList.get(position).id!!.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }


}