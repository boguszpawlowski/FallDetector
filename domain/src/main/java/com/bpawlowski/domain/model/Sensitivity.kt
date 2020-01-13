package com.bpawlowski.domain.model

enum class Sensitivity(
    val maxAcc: Float,
    val noMovementPeriod: Long,
    val maxAccWhenNotMoving: Float,
    val meanAccDuringNoMovement: Int
) {
    LOW(8f, 20, 20F, 10),
    MEDIUM(4f, 15, 10F, 8),
    HIGH(2f, 10, 20F, 5)
}
