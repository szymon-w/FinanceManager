package pl.edu.pja.financemanager.db

import androidx.room.*

@Dao
interface PositionDao {

    @Query("Select * from Position")
    fun getAll():List<Position>

    @Query("Select * from Position where strftime('%Y',date)=:year and strftime('%M',date)=:month")
    fun getAllForChosenMonth(month:Int, year:Int): List<Position>

    @Query("Select sum(amount) from Position where strftime('%Y',date)=:year and strftime('%M',date)=:month")
    fun getSumForChosenMonth(month:Int, year:Int): Double

    @Query("Select * from Position where id=:id")
    fun getPosition(id: Int): Position

    @Insert
    fun insert (position:Position)

    @Update
    fun update (position: Position)

    @Delete
    fun delete (position: Position)

}