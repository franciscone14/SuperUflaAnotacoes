package com.ufla.superuflaanotaes

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.ufla.superuflaanotaes.enums.Periodicidade
import com.ufla.superuflaanotaes.models.Nota
import kotlinx.android.synthetic.main.activity_nota.*
import java.time.LocalDate
import java.time.LocalTime
import java.util.*


class NotaActivity : AppCompatActivity() {

    private var mYear:Int? = null
    private var mMonth:Int? = null
    private var mDay:Int? = null
    private var mHour:Int? = null
    private var mMinute:Int? = null
    private var anotacao: Nota? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)
//        idText.visibility = View.INVISIBLE
//        idInput.visibility = View.INVISIBLE

        ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Periodicidade.values()).also { adapter ->
            periodicidadeSpinner.adapter = adapter
        }
        var nota:Nota? = null
        nota = intent?.getSerializableExtra("nota") as? Nota

        if (nota != null){
            idText.visibility = View.VISIBLE
            idInput.visibility = View.VISIBLE

            anotacao = nota
            idInput.setText(nota.id.toString())
            tituloInput.setText(nota.titulo)
            resumoInput.setText(nota.resumo)
            descricaoInput.setText(nota.descricao)
            periodicidadeSpinner.setSelection(nota.periodicidade.ordinal)
            textDateTime.setText(nota.dataAlerta.toString())

            mMinute = nota.hora?.minute
            mHour = nota.hora?.hour

            mYear = nota.dataAlerta?.year
            mMonth = nota.dataAlerta?.monthValue
            mDay = nota.dataAlerta?.dayOfMonth

            btnSalvar.text = "Atualizar"
            btnSalvar.setOnClickListener { view ->
                anotacao!!.id = idInput.text.toString().toLong()
                anotacao!!.titulo = tituloInput.text.toString()
                anotacao!!.resumo = resumoInput.text.toString()
                anotacao!!.descricao = descricaoInput.text.toString()
                anotacao!!.periodicidade = periodicidadeSpinner.selectedItem as Periodicidade
                anotacao!!.hora = LocalTime.of(mHour!!, mMinute!!)
                anotacao!!.dataAlerta = LocalDate.of(mYear!!,mMonth!!, mDay!!)
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("nota", anotacao)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    fun onSalvarClick(view: View){
        anotacao = Nota(
            null,
            tituloInput.text.toString(),
            resumoInput.text.toString(),
            descricaoInput.text.toString(),
            periodicidadeSpinner.selectedItem as Periodicidade,
            LocalTime.of(mHour!!, mMinute!!),
            LocalDate.of(mYear!!,mMonth!!, mDay!!),
            LocalDate.now()
        )

        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("nota", anotacao)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    fun onCancelarClick(view: View){
        var intent: Intent = Intent(applicationContext, NotaActivity::class.java)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    fun onDateSelect(view: View){
        var c = Calendar.getInstance();
        if(view == btnDate){
            // Get Current Date
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            val datePickerDialog = DatePickerDialog(
                this,
                OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth -> textDateTime.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    mMonth = monthOfYear
                    mYear = year
                    mDay = dayOfMonth
                },
                mYear!!,
                mMonth!!,
                mDay!!
            )
            datePickerDialog.show()
        }
        else{
            // Get Current Time
            // Get Current Time
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog

            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                OnTimeSetListener { view, hourOfDay, minute ->
                    var text:String = textDateTime.text.toString()
                    text += " $hourOfDay:$minute"
                    textDateTime.text = text
                    mHour = hourOfDay
                    mMinute = minute
                },
                mHour!!,
                mMinute!!,
                true
            )
            timePickerDialog.show()
        }
    }

}