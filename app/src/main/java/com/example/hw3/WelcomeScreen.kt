package com.example.hw3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hw3.viewmodel.DataLoaderViewModel

@Composable
fun WelcomeScreen(navController: NavController, cityName: String?, viewModel: DataLoaderViewModel) {
    val cities = viewModel.liveDataWeather.observeAsState()

    Column (
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight().fillMaxWidth()
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(500.dp).fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.welcome_text),
            )
            cityName?.let {cityName ->
                cities.value?.let {cities ->
                    val response = cities[cityName]
                    response?.let {
                        response.weather?.let {
                            Text("Temperature in ${cityName}: ${response.weather.degrees} degrees")
                        }
                        response.error?.let {
                            Text("Error: ${response.error}")
                        }
                    }
                }
            }

        }
        Button(
            onClick = { navController.navigate("citiesView") },
            modifier = Modifier.paddingFromBaseline(0.dp, 50.dp).fillMaxWidth()
        ) {
            Text(text = "View Cities")
        }
    }
}
