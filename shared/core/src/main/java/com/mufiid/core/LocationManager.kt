package com.mufiid.core

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.mufiid.core.extensions.value
import com.mufiid.core.stateevent.StateEvent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class LocationManager(private val context: Context) {

    private val fusedLocationProvider: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val locationRequest = LocationRequest.create().apply {
        priority = PRIORITY_HIGH_ACCURACY
        interval = 1000
    }

    @SuppressLint("MissingPermission")
    fun getLocationFlow(): Flow<Location> {
        val callbackFlow = callbackFlow<Location> {
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    for (location in result.locations) {
                        trySend(location)
                    }
                }
            }

            fusedLocationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnCanceledListener {
                cancel("Canceled by user")
            }.addOnFailureListener {
                cancel("Get location failure $it")
            }

            awaitClose { fusedLocationProvider.removeLocationUpdates(locationCallback) }
        }

        return callbackFlow.distinctUntilChanged { old, new ->
            old.distanceTo(new) < 20F
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocationFlowEvent (): Flow<StateEvent<Location>> {
        println("FLOW => idle")
        val callbackFlow = callbackFlow<StateEvent<Location>> {
            println("FLOW => loading")
            trySendBlocking(StateEvent.Loading())

            val locationCallback = @SuppressLint("MissingPermission")
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    for (location in result.locations) {
                        println("FLOW => success")
                        trySendBlocking(StateEvent.Success(location))
                    }
                }
            }

            fusedLocationProvider.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            ).addOnCanceledListener {
                cancel("Canceled by user")
                trySendBlocking(StateEvent.Failure(IllegalStateException("Canceled by user")))
            }.addOnFailureListener {
                cancel("Get location failure $it")
                trySendBlocking(StateEvent.Failure(it))
            }

            awaitClose { fusedLocationProvider.removeLocationUpdates(locationCallback) }
        }

        return callbackFlow.distinctUntilChanged { old, new ->
            val distance = old.value?.distanceTo(new.value) ?: 0F
            distance < 10F
        }
    }
}