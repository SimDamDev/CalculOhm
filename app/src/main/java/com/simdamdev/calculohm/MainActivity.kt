package com.simdamdev.calculohm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simdamdev.calculohm.components.InnerOhmCircle
import com.simdamdev.calculohm.components.InputCard
import com.simdamdev.calculohm.components.OhmCircle
import com.simdamdev.calculohm.ui.theme.CalculOhmTheme
import com.simdamdev.calculohm.utils.State
import com.simdamdev.calculohm.utils.exactlyTwoChecked
import com.simdamdev.calculohm.utils.updateValues

val RES_UNIT = listOf("nΩ", "µΩ", "mΩ", "Ω", "kΩ", "MΩ", "GΩ")
val VOL_UNIT = listOf("nV", "µV", "mV", "V", "kV", "MV", "GV")
val AMP_UNIT = listOf("nA", "µA", "mA", "A", "kA", "MA", "GA")
val POW_UNIT = listOf("nW", "µW", "mW", "W", "kW", "MW", "GW")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}


@Composable
fun MyApp(content: @Composable () -> Unit) {
    CalculOhmTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }

    }
}

@Composable
fun MainContent() {
    val state = remember { mutableStateOf(State()) }
    val warningCardVisible = remember { mutableStateOf(true) }
    warningCardVisible.value = !exactlyTwoChecked(state.value)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        TopHeader()
        WarningCard(warningCardVisible.value)
        InputCards(state.value)
        Calculate(state.value)

    }

}


@Preview(showBackground = true)
@Composable
fun TopHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(200.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        OhmCircle(
            size = 200
        )
        InnerOhmCircle(
            size = 80
        )
    }
}


@Composable
fun WarningCard(
    isVisible: Boolean
) {
    if (isVisible) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(60.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp))),
            color = Color(0xFFFFF9C4)
        ) {
            Text(
                text = "Select and enter only two parameters and let the calculator handle the rest for you!",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }

    } else {
        Spacer(
            modifier = Modifier
                .height(60.dp)
                .padding(15.dp)
        )
    }
}

@Composable
fun InputCards(state: State) {
    val unitOptionsR = RES_UNIT
    val unitOptionsU = VOL_UNIT
    val unitOptionsI = AMP_UNIT
    val unitOptionsP = POW_UNIT

    InputCard(
        labelId = "Resistance (R)",
        unitOptions = unitOptionsR,
        checkboxState = state.checkbox.R,
        valueState = state.value.R,
        unitState = state.unit.R,
    )
    InputCard(
        labelId = "Power (P)",
        unitOptions = unitOptionsP,
        checkboxState = state.checkbox.P,
        valueState = state.value.P,
        unitState = state.unit.P,
    )
    InputCard(
        labelId = "Voltage (U)",
        unitOptions = unitOptionsU,
        checkboxState = state.checkbox.U,
        valueState = state.value.U,
        unitState = state.unit.U,
    )
    InputCard(
        labelId = "Current (I)",
        unitOptions = unitOptionsI,
        checkboxState = state.checkbox.I,
        valueState = state.value.I,
        unitState = state.unit.I,
    )
    exactlyTwoChecked(state)
}

@Composable
fun Calculate(
    state: State,
) {
    Button(
        onClick = {
            if (state.checkbox.R.value) {
                state.value.convertR()
            }
            if (state.checkbox.U.value) {
                state.value.convertU()
            }
            if (state.checkbox.I.value) {
                state.value.convertI()
            }
            if (state.checkbox.P.value) {
                state.value.convertP()
            }
            if (exactlyTwoChecked(state)) {
                updateValues(state)
            }
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF00A8E8),
            contentColor = Color.White
        )
    ) {
        Text(text = "Calculate")
    }
}




