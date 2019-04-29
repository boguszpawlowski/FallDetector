package bogusz.com.service.model

data class AccelerometerEvent(
    val timestamp: Long,
    val x: Float,
    val y: Float,
    val z: Float
)