package com.example.masakin.ui.mart.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Composable helper untuk menangani location permission & request
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(
    onPermissionGranted: (com.google.android.gms.location.FusedLocationProviderClient) -> Unit,
    onPermissionDenied: () -> Unit = {}
) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            onPermissionGranted(fusedLocationClient)
        } else {
            locationPermissions.launchMultiplePermissionRequest()
        }
    }
}

/**
 * Helper function untuk request current location dengan timeout handling
 * Menggunakan BALANCED_POWER_ACCURACY untuk hasil lebih baik
 */
@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient
): android.location.Location? = suspendCancellableCoroutine { continuation ->
    val cts = CancellationTokenSource()

    // Coba dulu lastLocation (lebih cepat)
    fusedLocationClient.lastLocation.addOnSuccessListener { lastLocation ->
        if (lastLocation != null) {
            // Jika ada lastLocation dan cukup baru (< 5 menit), pakai itu
            val locationAge = System.currentTimeMillis() - lastLocation.time
            if (locationAge < 5 * 60 * 1000) { // 5 minutes
                continuation.resume(lastLocation)
                return@addOnSuccessListener
            }
        }

        // Jika tidak ada atau terlalu lama, request fresh location
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY, // Lebih akurat dari HIGH_ACCURACY
            cts.token
        ).addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener { exception ->
            // Fallback ke lastLocation jika getCurrentLocation gagal
            continuation.resume(lastLocation)
        }
    }.addOnFailureListener { exception ->
        continuation.resumeWithException(exception)
    }

    continuation.invokeOnCancellation {
        cts.cancel()
    }
}

/**
 * Get address from coordinates using Geocoder
 */
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
                    val city = address?.locality ?: address?.subAdminArea ?: "Unknown"
                    val province = address?.adminArea ?: ""
                    continuation.resume(if (province.isNotEmpty()) "$city, $province" else city)
                }
            }
        } else {
            @Suppress("DEPRECATION")
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.firstOrNull()
            val city = address?.locality ?: address?.subAdminArea ?: "Unknown"
            val province = address?.adminArea ?: ""
            if (province.isNotEmpty()) "$city, $province" else city
        }
    } catch (e: Exception) {
        "Malang, Jawa Timur" // Fallback
    }
}