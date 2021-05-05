package pl.edu.pja.financemanager.db

import androidx.room.*

@Dao
interface PositionDao {

    @Query("Select * from Position")
    fun getAll():List<Position>

    @Query("Select * from Position where strftime('%Y',eventDate)=:eventYear and strftime('%m',eventDate)=:eventMonth ")
    fun getAllForChosenMonth(eventYear:String, eventMonth:String): List<Position>

    @Query("Select sum(amount) from Position where strftime('%Y',eventDate)=:eventYear and strftime('%m',eventDate)=:eventMonth ")
    fun getSumForChosenMonth(eventYear:String, eventMonth:String): Double

    @Query("Select * from Position where id=:id")
    fun getPosition(id: Int): Position

    @Insert
    fun insert (position:Position)

    @Update
    fun update (position: Position)

    @Delete
    fun delete (position: Position)

}