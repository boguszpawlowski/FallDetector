package bogusz.com.service.model

import androidx.room.TypeConverter

enum class UserPriority(val priority: Int) {
    PRIORITY_ICE(1),
    PRIORITY_NORMAL(0);

}

object UserPriorityConverter {
    @TypeConverter
    @JvmStatic
    fun toPriority(priority: Int): UserPriority {
        return when (priority) {
            0 -> UserPriority.PRIORITY_NORMAL
            1 -> UserPriority.PRIORITY_ICE
            else -> UserPriority.PRIORITY_NORMAL
        }
    }

    @TypeConverter
    @JvmStatic
    fun toInt(priority: UserPriority): Int = priority.priority
}