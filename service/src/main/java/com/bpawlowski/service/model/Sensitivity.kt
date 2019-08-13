package com.bpawlowski.service.model

import androidx.room.TypeConverter

enum class Sensitivity(
    val maxAcc: Float,
    val noMovementPeriod: Long,
    val maxAccWhenNotMoving: Float,
    val meanAccDuringNoMovement: Int
) {
    LOW(30f, 20, 20F, 10),
    MEDIUM(20f, 15, 10F, 8),
    HIGH(10f, 10, 20F, 5)
}

internal object SensitivityConverter {
    @TypeConverter
    @JvmStatic
    fun toSensitivity(sensitivity: Int): Sensitivity {
        return when (sensitivity) {
            0 -> Sensitivity.LOW
            1 -> Sensitivity.MEDIUM
            2 -> Sensitivity.HIGH
            else -> Sensitivity.HIGH
        }
    }

    @TypeConverter
    @JvmStatic
    fun toInt(sensitivity: Sensitivity): Int = sensitivity.ordinal
}