package com.example.circal_progress_bar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.circal_progress_bar.ui.theme.LightBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBar(
                    percentage = 0.9f,
                    number = 500,
                )
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float, //проценты
    fontSize: TextUnit = 28.sp,
    number: Int, //число из которого будем находить процентную часть
    radius: Dp = 50.dp, //грубо говоря эту штуку можно было бы заменить диаметром
    color: Color = LightBlue,
    strokeWeight: Dp = 8.dp, //толщина круговой полоски
    animDuration: Int = 1000, // 1 сек
    animDelay: Int = 0, // 0 сек
) {

    var animationPlayed by remember { mutableStateOf(false) } //ждем начала анимации

    val animateFloatState = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f, /* если делаем активным
        то устанавливаем проценты, если нет то 0*/
        animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay) //анимация
    ) //слежка за состоянием анимации


    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2)
    ) {
        Canvas(modifier = Modifier.size(radius * 2)) {
            drawArc( //рисует контур окружности
                color = color,
                -90f,
                360 * animateFloatState.value,
                useCenter = false,
                style = Stroke(strokeWeight.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (animateFloatState.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
