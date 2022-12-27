package com.example.calculohm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculohm.components.InnerOhmCircle
import com.example.calculohm.components.InputCard
import com.example.calculohm.components.InputField
import com.example.calculohm.components.OhmCirlceBox
import com.example.calculohm.ui.theme.CalculOhmTheme
import com.example.calculohm.widgets.SelectUnit

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
    val unitOptionsRes = listOf("mΩ","Ω", "kΩ", "MΩ", "GΩ")
    val unitOptionsPot = listOf("mW","W", "kW", "MW", "GW")
    val unitOptionsVolt = listOf("mV","V", "kV", "MV")
    val unitOptionsAmp = listOf("mA","A", "kA")
    val warningCardVisible = remember { mutableStateOf(true) }
    val checkBoxStateRes = remember { mutableStateOf(false) }
    val valueStateRes = remember { mutableStateOf("") }
    val unitStateRes = remember { mutableStateOf("")}
    val checkBoxStatePot = remember { mutableStateOf(false) }
    val valueStatePot = remember { mutableStateOf("") }
    val unitStatePot = remember { mutableStateOf("") }
    val checkBoxStateVolt = remember { mutableStateOf(false) }
    val valueStateVolt = remember { mutableStateOf("") }
    val unitStateVolt = remember { mutableStateOf("") }
    val checkBoxStateAmp = remember { mutableStateOf(false) }
    val valueStateAmp = remember { mutableStateOf("") }
    val unitStateAmp = remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        TopHeader()
        WarningCard(warningCardVisible.value)
        InputCard(
            labelId= "Resistance (R)",
            checkboxState = checkBoxStateRes,
            unitOptions = unitOptionsRes,
            valueState = valueStateRes,
            unitState = unitStateRes,
        )
        InputCard(
            labelId= "Power (P)",
            checkboxState = checkBoxStatePot,
            unitOptions = unitOptionsPot,
            valueState = valueStatePot,
            unitState = unitStatePot,
        )
        InputCard(
            labelId= "Voltage (V)",
            checkboxState = checkBoxStateVolt,
            unitOptions = unitOptionsVolt,
            valueState = valueStateVolt,
            unitState = unitStateVolt,
        )
        InputCard(
            labelId= "Current (I)",
            checkboxState = checkBoxStateAmp,
            unitOptions = unitOptionsAmp,
            valueState = valueStateAmp,
            unitState = unitStateAmp,
        )


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
        OhmCirlceBox(
            size= 200
        )
        InnerOhmCircle (
            size = 80
        )
    }
}


@Composable
fun WarningCard(
    isVisible: Boolean
){
    if (isVisible) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(60.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp))),
            color = Color(0xFFFFF9C4)
        ){
            Text(
                text= "Select and enter only two parameters and let the calculator handle the rest for you!",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}





