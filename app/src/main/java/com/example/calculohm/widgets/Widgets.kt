package com.example.calculohm.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// a composable function that draws a circle with the color passed as parameter
@Composable
fun CircleBox(
    size: Int = 100,
    color: Color = Color.White,
    outlineColor : Color = Color.Black,
    outline : Boolean = false,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier
                .padding(5.dp)
                .size(size.dp),
            color = color,
            border = if(outline) {BorderStroke(2.dp, outlineColor)} else {null}
        ) {
            content()
        }
    }
}


// a composable function that draws a arc of circle with a given angle and color
@Composable
fun DrawArc(
    color: Color = Color.Red,
    startAngle: Float = 0f,
    sweepAngle: Float = 90f,
) {
    Canvas(modifier = Modifier) {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
        )
    }

}



// a composable function to draw a grid of text in a circle (6*2)
@Composable
fun TextGrid(
    height: Dp,
    width: Dp,
    texts: List<String>
) {
    val horizontalSpacer = height.value / 8
    val widthSpacer = width.value / 8
    val widthSpacings = listOf(1.5f, 4f, 5f, 5f, 4f, 1.5f)
    val fontSize = width.value / 16

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (i in 1..6) {
            Row(
                modifier = Modifier.height(horizontalSpacer.dp)
            ) {
                val currentSpacing = widthSpacer * widthSpacings[i - 1]

                Text(
                    text = texts[i * 2 - 2],
                    style = TextStyle(fontSize = fontSize.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(horizontalSpacer.dp)
                )
                Spacer(Modifier.size(width = currentSpacing.dp, height = 0.dp))
                Text(
                    text = texts[i * 2 - 1],
                    style = TextStyle(fontSize = fontSize.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(horizontalSpacer.dp)
                )
            }
        }
    }
}

// a composable function to draw a grid of text in a circle (2*2)
@Composable
fun InnerTextGrid(
    height: Dp,
    width: Dp,
    texts: List<String>
) {
    val hSpacer = height.value / 3
    val wSpacer = width.value / 4
    val wSpacings = listOf(1f, 1f)
    val fontSize = width.value / 4

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (i in 1..2) {
            Row(
                modifier = Modifier.height(hSpacer.dp)
            ) {
                val currentSpacing = wSpacer * wSpacings[i - 1]

                Text(
                    text = texts[i * 2 - 2],
                    style = TextStyle(fontSize = fontSize.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(hSpacer.dp)
                )
                Spacer(Modifier.size(width = currentSpacing.dp, height = 0.dp))
                Text(
                    text = texts[i * 2 - 1],
                    style = TextStyle(fontSize = fontSize.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(hSpacer.dp)
                )
            }
        }
    }
}

// a composable function that create a drop down menu
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectUnit(
    modifier: Modifier = Modifier,
    options : List<String> = emptyList(),
    selectedUnit: MutableState<String>
){
    val expanded =  remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        expanded = expanded.value,
        onExpandedChange = {
            expanded.value = !(expanded.value)
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedUnit.value,
            onValueChange = { },
            label = { Text("Unit") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded.value
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedUnit.value = selectionOption
                        expanded.value = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }

}