package com.example.calculohm.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.BaselineShift

class ColorList(
    val col1: Color,
    val col2: Color,
    val col3: Color,
    val col4: Color,
){
}


val superscript = SpanStyle(
    baselineShift = BaselineShift.Superscript
)