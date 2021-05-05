package pl.edu.pja.financemanager.db

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun dateToString (value:String): LocalDate {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun stringToDate(value:LocalDate): String{
        return value.toString()
    }
}