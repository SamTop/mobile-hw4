package com.example.hw3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.hw3.viewmodel.DataLoaderViewModel

data class CityInfo(val name: String, val description: String, val imgUrl: String)

@Composable
fun CitiesScreen(navController: NavController, viewModel: DataLoaderViewModel) {
    val cities = arrayOf(
        CityInfo("Yerevan", stringResource(R.string.city1_desc), stringResource(R.string.city1_url)),
        CityInfo("Moscow", stringResource(R.string.city2_desc), stringResource(R.string.city2_url)),
        CityInfo("Amsterdam", stringResource(R.string.city3_desc), stringResource(R.string.city3_url)),
    )

    Column (
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.height(600.dp)
        ) {
            Column (
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight =1f, fill = false),
            ) {
                cities.forEach { city ->
                    CityView(city, viewModel)
                }
            }
        }
        Button(
            onClick = { navController.navigate("welcomeView") },
            modifier = Modifier
                .paddingFromBaseline(0.dp, 50.dp)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.btn_back))
        }
    }
}

fun fetchCity(viewModel: DataLoaderViewModel, cityName: String) {
    viewModel.loadWeather(cityName, "84bbfd6562ad49c9964175819231111")
}

@Composable
fun CityView(city: CityInfo, viewModel: DataLoaderViewModel) {
    val cities = viewModel.liveDataWeather.observeAsState()
    fetchCity(viewModel, city.name)

    Column (modifier = Modifier.padding(vertical = 15.dp)) {
        Text(
            text = city.name,
            fontSize = 30.sp
        )
        Text(text = city.description)
        cities.value?.let {cities ->
            val response = cities[city.name]
            response?.let {
                response.weather?.let {
                    Text("${response.weather.degrees} degrees")
                }
                response.error?.let {
                    Text("Error: ${response.error}")
                }
            }
        }
        AsyncImage(model = city.imgUrl, contentDescription = "")
    }
}
