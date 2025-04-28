package com.example.meonmap.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

class Utils(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun requestUpdatePosition(viewModel: PositionViewModel) {
        try {
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                1000
            ).build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.lastLocation?.let { location ->
                        Log.d("Location", "New location: ${location.latitude}, ${location.longitude}")
                        viewModel.updatePosition(
                            PositionData(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        )
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: Exception) {
            Log.e("Location", "Error requesting location updates", e)
        }
    }

    fun getAddress(position: PositionData): String{
        val geocoder = Geocoder(context)
        val address: MutableList<Address>? = geocoder.getFromLocation(position.latitude, position.longitude, 1)
        return if (address?.isNotEmpty()==true){address[0].getAddressLine(0)}else{"No address on this position!"}
    }

}