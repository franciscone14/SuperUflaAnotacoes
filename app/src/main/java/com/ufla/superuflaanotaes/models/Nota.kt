package com.ufla.superuflaanotaes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ufla.superuflaanotaes.converters.NotaConverter
import com.ufla.superuflaanotaes.enums.Periodicidade
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "Nota")
@TypeConverters(NotaConverter::class)
data class Nota constructor(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name="titulo") var titulo: String,
    @ColumnInfo(name="resumo") var resumo: String,
    @ColumnInfo(name="descricao") var descricao: String,
    @ColumnInfo(name="periodicidade") var periodicidade: Periodicidade,
    @ColumnInfo(name="hora") var hora: LocalTime?,
    @ColumnInfo(name="data_alerta") var dataAlerta: LocalDate?,
    @ColumnInfo(name="data_cadastro") val dataDeCadastro: LocalDate?
) : Serializable