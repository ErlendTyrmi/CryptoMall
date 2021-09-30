package com.erlend.CryptoMall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erlend.CryptoMall.ui.theme.CryptoMallTheme
import com.erlend.CryptoMall.ui.theme.SplashYellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoMallTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Splash(800)
                }
            }
        }
    }
}

@Composable
fun Splash(duration: Int) {
    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(SplashYellow)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center).padding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.mall),
                modifier = Modifier
                    .fillMaxWidth(),
                contentDescription = "Mall Logo"
            )
            Text(
                text = "CryptoMall",
                style = MaterialTheme.typography.h1,
                //modifier = Modifier
                //    .fillMaxWidth()
                //    .align(Alignment.CenterHorizontally),

                //modifier = Modifier.align(Alignment.Center)
                //.scale(scale.value)
            )
        }
    }

    // Making the user aware that this is just a project app
    Text(text = "Demo App only")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoMallTheme {
        Splash(800)
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()

    ) {
        Text(
            text = "Main Screen",
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(Color.Black, fontSize = 28.sp)
        )
    }
}