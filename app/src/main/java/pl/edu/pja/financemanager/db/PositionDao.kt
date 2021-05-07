package pl.edu.pja.financemanager.db

import android.database.Cursor
import androidx.room.*

@Dao
interface PositionDao {

    @Query("Select * from Position order by eventDate")
    fun getAll():List<Position>

    @Query("Select * from Position where strftime('%Y',eventDate)=:eventYear and strftime('%m',eventDate)=:eventMonth  order by eventDate ")
    fun getAllForChosenMonth(eventYear:String, eventMonth:String): List<Position>

    @Query("Select sum(amount) from Position where strftime('%Y',eventDate)=:eventYear and strftime('%m',eventDate)=:eventMonth  order by eventDate ")
    fun getSumForChosenMonth(eventYear:String, eventMonth:String): Double

    @Query("Select * from Position where id=:id")
    fun getPosition(id: Int): Position

    @Insert
    fun insert (position:Position): Long

    @Update
    fun update (position: Position): Int

    @Query("Delete from Position where id=:id ")
    fun deleteById (id: Int): Int

    //Content provider
    @Query("Select * from Position order by eventDate")
    fun getAllCursor(): Cursor

    @Query("Select * from Position where id=:id")
    fun getPositionByIdCursor(id: Long): Cursor



}