package pl.edu.pja.financemanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Position(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var place: String,
    var category: String,
    var date: LocalDate,
    var amount: Double
)
