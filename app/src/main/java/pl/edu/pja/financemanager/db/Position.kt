package pl.edu.pja.financemanager.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Position(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    var place: String,
    var category: String,
    var eventDate: LocalDate,
    var amount: Double
)
