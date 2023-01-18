package com.apearmy.reversetimecounter.presentation


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.lang.Math.PI


@Composable
fun ReverseTimer(
    daysToEndDate: Long,
    hoursToEndDate: Long,
    minutesToEndDate: Long,
    secondsToEndDate: Long,
    daysToEndDateFrom: Long,
    hoursToEndDateFrom: Long,
    minutesToEndDateFrom: Long,
    secondsToEndDateFrom: Long,
    minModulo: Int,
    hourModulo: Int,
    dayModulo: Int
) {

    Surface(
        color = Color(0xFF101010),
        modifier = Modifier.fillMaxSize()
    ) {
        //val constraints = ConstraintSet {}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                ReverseTimerItemRef(
                    text = "Days",
                    totalTime = daysToEndDateFrom * 1000L,
                    curTime = daysToEndDate * 1000L,
                    modulo = dayModulo,
                    unitOfTimeMultiplier = 86400,
                    handleColor = Color.Green,
                    inactiveBarColor = Color.DarkGray,
                    activeBarColor = Color(0xFF37B900),
                    modifier = Modifier.size(150.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
            ) {
                ReverseTimerItemRef(
                    text = "Hours",
                    totalTime = hoursToEndDateFrom * 1000L,
                    curTime = hoursToEndDate * 1000L,
                    modulo = hourModulo,
                    unitOfTimeMultiplier = 3600,
                    handleColor = Color.Green,
                    inactiveBarColor = Color.DarkGray,
                    activeBarColor = Color(0xFF37B900),
                    modifier = Modifier.size(150.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
            ) {
                ReverseTimerItemRef(
                    text = "Minutes",
                    totalTime = minutesToEndDateFrom * 1000L,
                    curTime = minutesToEndDate * 1000L,
                    modulo = minModulo,
                    unitOfTimeMultiplier = 60,
                    handleColor = Color.Green,
                    inactiveBarColor = Color.DarkGray,
                    activeBarColor = Color(0xFF37B900),
                    modifier = Modifier.size(150.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
            ) {
                ReverseTimerItemRef(
                    text = "Seconds",
                    totalTime = secondsToEndDateFrom * 1000L,
                    curTime = secondsToEndDate * 1000L,
                    modulo = 0,
                    unitOfTimeMultiplier = 1,
                    handleColor = Color.Green,
                    inactiveBarColor = Color.DarkGray,
                    activeBarColor = Color(0xFF37B900),
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    }
}


//================================================================================================================================
//================================================================================================================================

// 1. initialValue = (remainingTime.toFloat() / totalTime) or something like that        DONE
// 2. Να βαλω μια μεταβλητη currentTime για να υπολογιζεται το angle σωστα               DONE
// 3. Να αλλάξω το isTimerRunning σε curTime > ???, λογικά > 0                           DONE
// 4. Να φτιάξω μεταβλητες για customizable delay & display time                         DONE
// 5. Να κανω implement καποιο modulo                                                    DONE



@Composable
fun ReverseTimerItemRef(
    text: String,                               //Seconds, Minutes, Hours, Days
    totalTime: Long,                            //Time that will be displayed in the circle
    curTime: Long,
    modulo: Int = 0,
    unitOfTimeMultiplier: Int,
    handleColor: Color,                         //Χρώμα κύκλου στη γραμμή του counter
    inactiveBarColor: Color,                    //inactive Bar Color
    activeBarColor: Color,                      //Active Bar Color
    modifier: Modifier = Modifier,
    initialValue: Float = 1f,                    //Percentage of where the bar starts
    strokeWidth: Dp = 5.dp                       //Πάχος γραμμής
) {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    var value by remember {                     //Percentage of bar that is filled
        mutableStateOf(curTime.toFloat() / totalTime)
    }

    var currentTime by remember {
        mutableStateOf(curTime)
    }

    var isTimerRunning by remember {            //If endDate < .now() ή κάτι με τa remaining
        mutableStateOf(curTime > 0)
    }

    var mdl by remember {
        mutableStateOf(modulo)
    }


    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            if (mdl > 0) {
                delay(1000L * mdl.toLong())
                mdl = 0
            }
            delay(100L * unitOfTimeMultiplier)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .onSizeChanged {
                size = it
            }
    ) {
        Canvas(modifier = modifier) {
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f * value,
                useCenter = false,
                size = Size(size.width.toFloat(), size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            //Angle
            val center = Offset(size.width / 2f, size.height / 2f)
            val beta = (250f * value + 145f) * (PI / 180f).toFloat()
            val r = size.width / 2f
            val a = kotlin.math.cos(beta) * r
            val b = kotlin.math.sin(beta) * r

            drawPoints(
                listOf(Offset(center.x + a, center.y + b)),
                pointMode = PointMode.Points,
                color = handleColor,
                strokeWidth = (strokeWidth * 3f).toPx(),
                cap = StrokeCap.Round
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (currentTime / 1000L).toString(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}




