package com.example.masakin.ui.mart.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Simplified Location Helper
 */

@SuppressLint("MissingPermission") // Permission should be checked before calling this
suspend fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient
): Location? = suspendCancellableCoroutine { continuation ->
    val cts = CancellationTokenSource()
    
    try {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            cts.token
        ).addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener {
            continuation.resume(null)
        }
    } catch (e: Exception) {
        continuation.resumeWithException(e)
    }

    continuation.invokeOnCancellation {
        cts.cancel()
    }
}

suspend fun getAddressFromLocation(
    context: Context,
    latitude: Double,
    longitude: Double
): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { continuation ->
                geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                    val address = addresses.firstOrNull()
                    val result = address?.getAddressLine(0) ?: "Unknown Location"
                    continuation.resume(result)
                }
            }
        } else {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
        }
    } catch (e: Exception) {
        "Location Unavailable"
    }
}