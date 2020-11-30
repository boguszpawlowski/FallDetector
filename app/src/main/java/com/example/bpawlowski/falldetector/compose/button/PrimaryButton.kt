package com.example.bpawlowski.falldetector.compose.button

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.bpawlowski.falldetector.compose.FallDetectorTheme

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    text: String = "",
    onClick: () -> Unit = {},
) {
    Card(
        backgroundColor = backgroundColor,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.height(50.dp).width(150.dp) then modifier
    ) {
        ConstraintLayout {
            val (label, shape) = createRefs()

            Text(text = text, color = textColor, modifier = Modifier.constrainAs(label) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })

            Box(
                modifier = Modifier.width(30.dp)
                    .height(5.dp)
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(2.dp)
                    ).constrainAs(shape) {
                        top.linkTo(label.bottom)
                        start.linkTo(label.start)
                        end.linkTo(label.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}


@Preview
@Composable
fun PrimaryButtonPreview() {
    FallDetectorTheme {
        PrimaryButton(
            onClick = {},
            text = "Button"
        )
    }
}