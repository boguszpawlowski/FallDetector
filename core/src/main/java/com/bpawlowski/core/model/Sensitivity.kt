package com.bpawlowski.core.model

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