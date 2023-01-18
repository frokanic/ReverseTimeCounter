package com.apearmy.reversetimecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.apearmy.reversetimecounter.presentation.ReverseTimeCounterViewModel
import com.apearmy.reversetimecounter.presentation.ReverseTimer
import com.apearmy.reversetimecounter.ui.theme.ReverseTimeCounterTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ReverseTimeCounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReverseTimeCounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ReverseTimer(
                        daysToEndDate = viewModel.daysToEndDate,
                        hoursToEndDate = viewModel.hoursToEndDate,
                        minutesToEndDate = viewModel.minutesToEndDate,
                        secondsToEndDate = viewModel.secondsToEndDate,
                        daysToEndDateFrom = viewModel.daysToEndDateFrom,
                        hoursToEndDateFrom = viewModel.hoursToEndDateFrom,
                        minutesToEndDateFrom = viewModel.minutesToEndDateFrom,
                        secondsToEndDateFrom = viewModel.secondsToEndDateFrom,
                        minModulo = viewModel.minModulo,
                        hourModulo = viewModel.hourModulo,
                        dayModulo = viewModel.dayModulo,
                    )
                }
            }
        }
    }
}

