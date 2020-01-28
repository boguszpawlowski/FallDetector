package com.example.bpawlowski.falldetector.screens.common.compose

import androidx.compose.foundation.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding

@Composable
fun HomeScreen(onClick: () -> Unit) {
    FallDetectorTheme {
        IconScreen(icon = Icons.Default.Security, onClick = onClick)
    }
}

@Composable
fun AlarmScreen(onClick: () -> Unit) {
    FallDetectorTheme {
        IconScreen(icon = Icons.Default.PriorityHigh, onClick = onClick)
    }
}

@Composable
fun IconScreen(icon: VectorAsset, onClick: () -> Unit) {
    Card(shape = CircleShape, modifier = Modifier.wrapContentSize(), elevation = 8.dp) {
        Icon(
            asset = icon.copy(defaultHeight = 100.dp, defaultWidth = 100.dp),
            modifier = Modifier.clickable(onClick = onClick).padding(Padding.Large)
        )
    }
}

@Preview
@Composable
fun IconScreenPreview() {
    IconScreen(icon = Icons.Default.AddAlert) {}
}
