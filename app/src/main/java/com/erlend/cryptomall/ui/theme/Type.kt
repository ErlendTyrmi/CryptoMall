/*
 * Copyright (c) 2021. Erlend Tyrmi
 */

package com.erlend.cryptomall.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.erlend.cryptomall.R

// Custom Ubuntu font
val ubuntu = FontFamily (
    Font(R.font.ubuntu_regular, weight = FontWeight.Normal),
    Font(R.font.ubuntu_medium, weight = FontWeight.Medium),
    Font(R.font.ubuntu_bold, weight = FontWeight.Bold),
    Font(R.font.ubuntu_light, weight = FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = ubuntu,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),


/* Other default text styles to override
button = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp
),
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)