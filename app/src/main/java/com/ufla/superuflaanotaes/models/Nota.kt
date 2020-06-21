package com.ufla.superuflaanotaes.models

import android.os.Build
import androidx.annotation.RequiresApi
import com.ufla.superuflaanotaes.enums.Periodicidade
import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.persistence.*

class Nota constructor(
    val id: Int,
    var titulo: String,
    var resumo: String,
    var descricao: String,
    var periodicidade: Periodicidade,
    var hora: LocalTime?,
    var dataAlerta: LocalDateTime?,
    val dataDeCadastro: LocalDateTime?
){




}