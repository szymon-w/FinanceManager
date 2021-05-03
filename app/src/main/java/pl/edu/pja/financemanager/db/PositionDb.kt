package pl.edu.pja.financemanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version=1, entities = [Position::class])
@TypeConverters(Converters::class)
abstract class PositionDb: RoomDatabase() {
    abstract fun positions(): PositionDao
}