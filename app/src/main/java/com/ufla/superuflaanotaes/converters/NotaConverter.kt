package com.ufla.superuflaanotaes.converters

import androidx.room.TypeConverter
import com.ufla.superuflaanotaes.enums.Periodicidade
import java.time.LocalDate
import java.time.LocalTime

class NotaConverter {
    @TypeConverter
    fun fromStringDate(value: String?): LocalDate? {

        if(value == null) return null

        return LocalDate.parse(value)
    }

    @TypeConverter
    fun toStringDate(timestamp: LocalDate?): String? {

        if(timestamp == null) return null

        return timestamp.toString()
    }

    @TypeConverter
    fun fromName(value: String?): Periodicidade? {

        if(value == null) return null

        return Periodicidade.valueOf(value)
    }

    @TypeConverter
    fun toName(value: Periodicidade?): String? {

        if(value == null) return null

        return value.name
    }

    @TypeConverter
    fun fromTimestamp(value: String?): LocalTime? {

        if(value == null) return null

        return LocalTime.parse(value)
    }

    @TypeConverter
    fun toTimestamp(timestamp: LocalTime?): String? {

        if(timestamp == null) return null

        return timestamp.toString()
    }
}