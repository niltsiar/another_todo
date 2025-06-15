package dev.niltsiar.anothertodo.data.local

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime

class DateTimeConverters {

    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    @TypeConverter
    fun toString(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }
}
