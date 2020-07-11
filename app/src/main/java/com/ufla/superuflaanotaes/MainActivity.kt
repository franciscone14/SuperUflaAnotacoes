package com.ufla.superuflaanotaes

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ufla.superuflaanotaes.adapters.NotaAdapter
import com.ufla.superuflaanotaes.dao.AppDatabase
import com.ufla.superuflaanotaes.dao.NotaDao
import com.ufla.superuflaanotaes.models.Nota
import com.ufla.superuflaanotaes.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private var notaDao: NotaDao? = null
    private var CHANNEL_ID: String = "Default 1"
    private var notificationId: Int = 0

    private var notas: ArrayList<Nota> = ArrayList<Nota>()
    private var adapterNota: NotaAdapter? = null
    private var selected: Int? = null

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        db = AppDatabase.getAppDataBase(context = this)
        notaDao = db?.userDao()

        notas = db?.userDao()?.getAll() as ArrayList<Nota>

        NotaAdapter(applicationContext, notas).also { adapter ->
            adapterNota = adapter
            listView.adapter = adapter
        }

        //Open the edit screen
        listView.setOnItemClickListener { _, _, position, _ ->
            selected = position
            var nota:Nota = notas[position]
            var intent: Intent = Intent(applicationContext, NotaActivity::class.java)
            intent.putExtra("nota", nota)
            startActivityForResult(intent, 1)
        }

        // Select item to perform an action
        listView.setOnItemLongClickListener { _, _, position, _ ->
            selected = position
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_CANCELED){
            selected = null
        }
        else{
            var nota: Nota? = data?.getSerializableExtra("nota") as? Nota

            if(selected == null && nota != null) {
                notas.add(nota)
                nota.id = db!!.userDao().insert(nota)

                Utils.createAlarm(
                    this,
                    "Você tem uma atividade marcada para agora",
                    nota.titulo,
                    nota
                )
            }
            else if(nota != null && selected != null){
                notas.removeAt(selected!!)
                notas.add(nota)
                db!!.userDao().update(nota)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapterNota?.notifyDataSetChanged()
    }

    fun onDeleteClick(view: View){
        if (selected != null){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deseja realmente apagar essa anotação ?")
                .setPositiveButton("Confirmar",
                    DialogInterface.OnClickListener { dialog, id ->
                        db!!.userDao().delete(notas[selected!!])
                        notas.removeAt(selected!!)
                        adapterNota?.notifyDataSetChanged()
                        selected = null
                    })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener{dialog, which ->  selected = null})
            builder.create().show()
        }
        else{
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Você deve selecionar uma anotação para ser excluida ! " +
                    "Dica: Para fazer isso basta precionar e segurar por alguns segundos")
            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    fun onExitClick(view: View){
        exitProcess(1)
    }

    fun onSortClick(view: View){
        var opcoes = arrayOf("Id", "Titulo", "Resumo", "Descrição", "Data Cadastro")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escolha um opção de ordenação das notas")
            .setItems(opcoes,
                DialogInterface.OnClickListener { dialog, which ->
                    when(which){
                        0 -> notas.sortBy({ nota -> nota.id})
                        1 -> notas.sortBy { nota -> nota.titulo }
                        2 -> notas.sortBy { nota -> nota.resumo }
                        3 -> notas.sortBy { nota -> nota.descricao }
                        4 -> notas.sortBy { nota -> nota.dataDeCadastro }
                    }
                    adapterNota?.notifyDataSetChanged()
                })
        builder.create().show()
    }

    fun inserirClick(view: View){
        selected = null
        val inserirActivity = Intent(applicationContext, NotaActivity::class.java)
        startActivityForResult(inserirActivity, 1)
    }

}