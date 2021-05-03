package pl.edu.pja.financemanager.db

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun dateToNumber (value:Long): LocalDate {
        return LocalDate.ofEpochDay(value)
    }

    @TypeConverter
    fun numberToDate(value:LocalDate): Long{
        return value.toEpochDay()
    }
}