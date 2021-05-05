package pl.edu.pja.financemanager.helper

import java.time.LocalDate

class DateHelper {

    companion object{
        fun getStringNumberMonth (date:LocalDate) : String{
            val monthNumber = date.monthValue
            return if(monthNumber>=10)
                monthNumber.toString()
            else
                "0${monthNumber.toString()}"
        }
    }
}