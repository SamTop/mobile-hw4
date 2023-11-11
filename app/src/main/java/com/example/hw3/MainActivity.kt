package com.example.hw3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hw3.ui.theme.HW3Theme
import com.example.hw3.viewmodel.DataLoaderViewModel
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {
    private val viewModel: DataLoaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HW3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavScreen()
                }
            }
        }
    }

    @Composable
    fun MainNavScreen() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "welcomeView") {
            composable("welcomeView") { WelcomeScreen(navController) }
            composable("citiesView") { CitiesScreen(navController, viewModel) }
        }
    }
}

