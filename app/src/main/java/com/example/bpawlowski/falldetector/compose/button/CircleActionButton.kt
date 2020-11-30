package com.example.bpawlowski.falldetector.compose.button

import androidx.compose.foundation.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sms
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import com.example.bpawlowski.falldetector.compose.Padding

@Composable
fun CircleActionButton(
    icon: VectorAsset,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(40.dp)
            .background(
                color = Color.DarkGray,
                shape = CircleShape
            ).padding(Padding.ExtraSmall) then modifier
    ) {
        Icon(asset = icon, tint = Color.White)
    }
}

@Preview
@Composable
fun CircleActionButtonPreview() {
    FallDetectorTheme {
        CircleActionButton(icon = Icons.Default.Sms)
    }
}