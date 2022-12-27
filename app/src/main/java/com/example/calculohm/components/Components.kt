package com.example.calculohm.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculohm.utils.ColorList
import com.example.calculohm.widgets.*

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
    )
}


@Composable
fun OhmCirlceBox(
    size: Int = 200,
    colors: ColorList =
        ColorList(
            Color(0xFFF44336),
            Color(0xFFFF9800),
            Color(0xFF4CAF50),
            Color(0xFF2196F3),
        ),

) {
    val texts : List<String> =
    listOf(
        "V·I", "P/V",
        "V²/R", "√(P/R)",
        "I²·R", "V/R",
        "I·R", "V/I",
        "P/I", "P/I²",
        "√(P·R)", "V²/P",
    )


    CircleBox(
        size = size,
        content = {
            drawArcCol(
                startAngle = 0f,
                sweepAngle = 90f,
                color = colors.col1
            )
            drawArcCol(
                startAngle = 90f,
                sweepAngle = 90f,
                color = colors.col2
            )
            drawArcCol(
                startAngle = 180f,
                sweepAngle = 90f,
                color = colors.col3
            )
            drawArcCol(
                startAngle = 270f,
                sweepAngle = 90f,
                color = colors.col4
            )
            TextGrid(size.dp, size.dp, texts)
        }
    )

}
@Preview
@Composable
fun InnerOhmCircle(
    size: Int = 50,
    colors: ColorList =
        ColorList(
            Color(0xFFF44336),
            Color(0xFFFF9800),
            Color(0xFF4CAF50),
            Color(0xFF2196F3),
        ),
    texts: List<String> =
        listOf(
            "P", "I",
            "V", "R",
        )
){
    OutlinedCircleBox(
        size = size,
        content = {
            drawArcCol(
                startAngle = 0f,
                sweepAngle = 90f,
                color = colors.col1
            )
            drawArcCol(
                startAngle = 90f,
                sweepAngle = 90f,
                color = colors.col2
            )
            drawArcCol(
                startAngle = 180f,
                sweepAngle = 90f,
                color = colors.col3
            )
            drawArcCol(
                startAngle = 270f,
                sweepAngle = 90f,
                color = colors.col4
            )
            innerTextGrid(
                height = size.dp, width = size.dp, texts = texts)
        }
    )
}

@Composable
fun InputCard(
    labelId : String,
    checkboxState: MutableState<Boolean>,
    unitOptions: List<String> = emptyList(),
    valueState : MutableState<String>,
    unitState : MutableState<String>
){

    Row (
        modifier = Modifier
            .requiredHeight(70.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxHeight()
                .size(30.dp),
            checked = checkboxState.value,
            onCheckedChange = { checked ->
                checkboxState.value = checked
            }
        )
        InputField(
            modifier = Modifier
                .fillMaxHeight()
                .size(200.dp),
            valueState = valueState,
            labelId = labelId,
            enabled = true,
            isSingleLine = true)
        SelectUnit(
            modifier = Modifier
                .fillMaxHeight()
                .size(150.dp),
            options = unitOptions,
            selectedUnit = unitState
        )
    }
}









