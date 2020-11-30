package com.example.bpawlowski.falldetector.compose

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Primary = Color(0xff424242)
val Secondary = Color(0xffff3400)
val Error = Color(0xffb00020)
val Red900 = Color(0xffc20029)
val Red200 = Color(0xfff297a2)
val Red300 = Color(0xffea6d7e)
val Red700 = Color(0xffdd0d3c)
val DarkGrey = Color(0xff363A3D)


val LightColors = lightColors(
    primary = Primary,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Secondary,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Error
)

val DarkColors = darkColors(
    primary = Primary,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Secondary,
    onSecondary = Color.White,
    background = DarkGrey,
    error = Error
)
