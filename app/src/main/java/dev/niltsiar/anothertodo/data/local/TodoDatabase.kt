package dev.niltsiar.anothertodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.niltsiar.anothertodo.data.model.TodoItem

@Database(entities = [TodoItem::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverters::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
