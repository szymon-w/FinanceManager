package pl.edu.pja.financemanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version=1, entities = [Position::class])
@TypeConverters(Converters::class)
abstract class PositionDb: RoomDatabase() {
    abstract fun positions(): PositionDao

    companion object{
        fun open(context: Context): PositionDb =
                Room.databaseBuilder(
                        context,
                        PositionDb::class.java,
                        "positions"
                ).build()
    }
}