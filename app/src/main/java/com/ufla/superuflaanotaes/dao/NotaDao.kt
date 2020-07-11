package com.ufla.superuflaanotaes.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ufla.superuflaanotaes.models.Nota

@Dao
interface NotaDao {
    @Query("SELECT * FROM Nota")
    fun getAll(): List<Nota>

    @Query("SELECT * FROM Nota WHERE id IN (:notaIds)")
    fun loadAllByIds(notaIds: IntArray): List<Nota>

    @Query("SELECT * FROM Nota WHERE titulo LIKE :titulo LIMIT 1")
    fun findByTitulo(titulo: String): Nota

    @Insert
    fun insert(nota: Nota): Long

    @Delete
    fun delete(nota: Nota)

    @Delete
    fun update(nota: Nota)
}