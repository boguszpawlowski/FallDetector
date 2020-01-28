package com.example.bpawlowski.falldetector.screens.common.compose

import androidx.compose.animation.animate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun ProfilePicture(isIce: Boolean, modifier: Modifier = Modifier) {
    Card(
        elevation = 5.dp,
        border = BorderStroke(
            width =  2.dp,
            color = animate(
                target = if (isIce) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
            )
        ),
        shape = CircleShape,
        modifier = modifier.size(200.dp)
    ) {
        CoilImage(
            data = "https://picsum.photos/300/300",
            fadeIn = true,
            modifier = Modifier.fillMaxSize().clickable(onClick = {}),
            loading = { CircularProgressIndicator(modifier = Modifier.size(30.dp)) }
        )
    }
}

@Composable
fun ProfileBackground() {
    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxWidth()
            .height(173.dp)
    ) {}
}

@Preview
@Composable
fun ProfilePicturePreview() {
    FallDetectorTheme {
        ProfilePicture(isIce = true)
    }
}
