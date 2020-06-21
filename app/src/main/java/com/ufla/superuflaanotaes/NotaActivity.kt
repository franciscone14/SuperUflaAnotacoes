package com.ufla.superuflaanotaes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.ufla.superuflaanotaes.enums.Periodicidade
import kotlinx.android.synthetic.main.activity_nota.*

class NotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        val adapter = ArrayAdapter<Periodicidade>(this, R.layout.activity_nota, Periodicidade.values())
        periodicidadeSpinner.adapter = adapter;
    }

}