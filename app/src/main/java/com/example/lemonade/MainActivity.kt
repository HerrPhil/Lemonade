package com.example.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeWithClickableImageAndInstruction(
        modifier = Modifier
            .fillMaxSize() // utilize space vertically
            .wrapContentSize(Alignment.Center) // position composition vertically
    )
}

@Composable
fun LemonadeWithClickableImageAndInstruction(modifier: Modifier = Modifier) {

    var step by remember { mutableStateOf(1) }

    var squeezes by remember { mutableStateOf(0)}

    val imageResource = when (step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val contentDescriptionResource = when (step) {
        1 -> R.string.lemon_tree_content_description
        2 -> R.string.lemon_content_description
        3 -> R.string.glass_of_lemonade_content_description
        else -> R.string.empty_glass_content_description
    }

    val instructionResource = when (step) {
        1 -> R.string.instruction_1
        2 -> R.string.instruction_2
        3 -> R.string.instruction_3
        else -> R.string.instruction_4
    }

    val accentButtonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(  R.color.teal_200)

    )

    // start adding UI widgets of the lemonade app

    // the outer UI widget is a column

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(onClick = {

            Log.i("lemonade", "step before action: $step")

            when (step) {
                1 -> {
                    squeezes = (2..4).random()
                    Log.i("lemonade", "initialize in step #1 squeezes: $squeezes")
                    step++
                }
                2 -> {
                    if (squeezes == 1) {
                        step++
                    } else {
                        squeezes--
                        Log.i("lemonade", "decrement in step #2 squeezes: $squeezes")
                    }
                }
                3 -> step++
                else -> step = 1 // when 4+
            }

            Log.i("lemonade", "step after action: $step")

        },
            shape = RoundedCornerShape(40.dp),
            border = BorderStroke(2.dp, Color(105,205,216,255)),
            colors = accentButtonColor
        ) {

            // Then add the image of the step of enjoying lemonade

            Image(
                painter = painterResource(imageResource),
                contentDescription = stringResource(contentDescriptionResource)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            stringResource(instructionResource),
            modifier = Modifier,
            fontSize = 18.sp,
            textAlign = TextAlign.Center

        )
    }
}
