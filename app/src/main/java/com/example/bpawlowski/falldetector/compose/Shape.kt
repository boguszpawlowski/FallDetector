package com.example.bpawlowski.falldetector.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Medium = RoundedCornerShape(Corners.Large, topRight = Corners.Large, bottomLeft = Corners.Large)
val Small = RoundedCornerShape(Corners.Medium)

object Corners {
    val Large = 20.dp
    val Medium = 15.dp
}

val shapes = Shapes(
    medium = Medium,
    small = Small
)

