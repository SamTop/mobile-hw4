package com.example.hw3

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.hw3.utils.PermissionUtils
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale
import android.os.Looper
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private const val LOCATION_PERMISSION_REQUEST_CODE =34

class MainActivity : ComponentActivity() {
    private val viewModel: DataLoaderViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    var cityName1: String = ""
    private val _liveDataCityName = MutableLiveData<String>()
    val liveDataCityName: LiveData<String> = _liveDataCityName
    private val _liveDataTemp = MutableLiveData<String>()
    val liveDataTemp: LiveData<String> = _liveDataTemp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
        val cityName = liveDataCityName.observeAsState()

        NavHost(navController, startDestination = "welcomeView") {
            composable("welcomeView") { WelcomeScreen(navController, cityName.value, viewModel) }
            composable("citiesView") { CitiesScreen(navController, viewModel) }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationListener() {
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = com.google.android.gms.location.LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    currentLocation = locationResult.lastLocation

                    var lat = currentLocation!!.latitude
                    var long = currentLocation!!.longitude
                    val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                    val addresses: MutableList<Address>? = geocoder.getFromLocation(lat, long, 1)
                    val test = addresses?.get(0)?.getAddressLine(0).toString()
                    _liveDataCityName.postValue(test)
                    fetchCity(viewModel, test)
                }
            },
            Looper.myLooper()!!
        )
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}

