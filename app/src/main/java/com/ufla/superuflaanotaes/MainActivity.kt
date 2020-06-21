package com.ufla.superuflaanotaes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ufla.superuflaanotaes.enums.Periodicidade
import com.ufla.superuflaanotaes.models.Nota
import java.time.LocalDateTime
import java.time.LocalTime

class MainActivity : AppCompatActivity() {

    lateinit var notas: List<Nota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    init {
        notas = listOf(
            Nota(
                1, "Teste",
                "Essa é minha primeira nota de teste",
                "Teste 1", Periodicidade.DIARIO,
                LocalTime.now(), LocalDateTime.now(), LocalDateTime.now()
            )
        )
    }

    public fun inserirClick(view: View){
        val inserirActivity = Intent(applicationContext, NotaActivity::class.java)
        startActivity(inserirActivity)
    }
}