package com.brunorochamoura.alwaysontimer

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

val GruvboxBackground = Color(0xFF282828)
val GruvboxText = Color(0xFFebdbb2)
val GruvboxAccent = Color(0xFFd79921)
val GruvboxDim = Color(0xFFa89984)
val GruvboxMedium = Color(0xFF3c3836)

@Composable
fun TimerScreen(onTimeUp: () -> Unit, stopAlarm: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("TimerPrefs", Context.MODE_PRIVATE)

    var hours by remember { mutableStateOf(sharedPreferences.getInt("hours", 0)) }
    var minutes by remember { mutableStateOf(sharedPreferences.getInt("minutes", 0)) }
    var seconds by remember { mutableStateOf(sharedPreferences.getInt("seconds", 0)) }

    LaunchedEffect(hours, minutes, seconds) {
        sharedPreferences.edit().apply {
            putInt("hours", hours)
            putInt("minutes", minutes)
            putInt("seconds", seconds)
            apply()
        }
    }

    var isRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(0L) }
    var showStopButton by remember { mutableStateOf(false) }

    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    fun startTimer() {
        val totalMillis = (hours * 3600 + minutes * 60 + seconds) * 1000L
        timeLeft = totalMillis
        isRunning = true

        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                isRunning = false
                showStopButton = true
                onTimeUp()
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        isRunning = false
        timeLeft = 0
        showStopButton = false
        stopAlarm()
    }

    fun cancelTimer() {
        timer?.cancel()
        timer = null
        isRunning = false
        timeLeft = 0
        showStopButton = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GruvboxBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp)
        ) {
            if (!isRunning && !showStopButton) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(GruvboxMedium)
                        .padding(8.dp)
                ) {
                    NumberPickerColumn(
                        "Hours", 0, 99, hours, { hours = it },
                        modifier = Modifier.weight(1f)
                    )
                    NumberPickerColumn(
                        "Minutes", 0, 59, minutes, { minutes = it },
                        modifier = Modifier.weight(1f)
                    )
                    NumberPickerColumn(
                        "Seconds", 0, 59, seconds, { seconds = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (isRunning || showStopButton) {
                val hrs = (timeLeft / 1000) / 3600
                val mins = ((timeLeft / 1000) % 3600) / 60
                val secs = (timeLeft / 1000) % 60

                Text(
                    text = String.format("%02d:%02d:%02d", hrs, mins, secs),
                    color = GruvboxText,
                    style = MaterialTheme.typography.displayLarge
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .height(84.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    !isRunning && !showStopButton -> {
                        Button(
                            onClick = { startTimer() },
                            enabled = (hours + minutes + seconds) > 0,
                            modifier = Modifier
                                .height(84.dp)
                                .width(180.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GruvboxAccent,
                                contentColor = GruvboxBackground,
                                disabledContainerColor = GruvboxDim,
                                disabledContentColor = GruvboxBackground.copy(alpha = 0.5f)
                            )
                        ) {
                            Text("Start")
                        }

                    }

                    isRunning -> {
                        Button(
                            onClick = { cancelTimer() },
                            modifier = Modifier
                                .height(84.dp)
                                .width(180.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GruvboxAccent)
                        ) {
                            Text("Cancel", color = GruvboxBackground)
                        }
                    }

                    showStopButton -> {
                        Button(
                            onClick = { stopTimer() },
                            modifier = Modifier
                                .height(84.dp)
                                .width(180.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GruvboxAccent)
                        ) {
                            Text("Stop Alarm", color = GruvboxBackground)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NumberPickerColumn(
    label: String,
    rangeStart: Int,
    rangeEnd: Int,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = label, color = GruvboxDim)

        AndroidView(
            factory = { context ->
                android.widget.NumberPicker(context).apply {
                    minValue = rangeStart
                    maxValue = rangeEnd
                    setOnValueChangedListener { _, _, newVal ->
                        onValueChange(newVal)
                    }
                    descendantFocusability = android.widget.NumberPicker.FOCUS_BLOCK_DESCENDANTS
                    setBackgroundColor(android.graphics.Color.parseColor("#3c3836"))
                    setTextColor(android.graphics.Color.parseColor("#ebdbb2"))
                }
            },
            update = { picker ->
                if (picker.value != value) {
                    picker.value = value
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
    }
}
