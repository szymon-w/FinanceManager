package pl.edu.pja.financemanager.helper

import java.time.LocalDate

class DateHelper {

    companion object {
        val monthMap = mapOf<String, String>(
                "January" to "01",
                "February" to "02",
                "March" to "03",
                "April" to "04",
                "May" to "05",
                "June" to "06",
                "July" to "07",
                "August" to "08",
                "September" to "09",
                "October" to "10",
                "November" to "11",
                "December" to "12"
        )

        fun getStringNumberMonth (date:LocalDate) : String{
            val monthNumber = date.monthValue
            return if(monthNumber>=10)
                monthNumber.toString()
            else
                "0${monthNumber.toString()}"
        }
    }


}