package com.example.masakin

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.example.masakin.ui.navigation.MasakinNavGraph
import com.example.masakin.ui.navigation.Routes
import com.example.masakin.ui.theme.MasakinTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // callback dari Compose setelah login Google sukses (untuk navigate ke HOME)
    private var onGoogleAuthSuccess: (() -> Unit)? = null

    // Launcher untuk Google Sign-In
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // SELALU coba ambil task dari intent, meskipun resultCode bukan RESULT_OK
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { signInTask ->
                    if (signInTask.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Berhasil login dengan Google",
                            Toast.LENGTH_SHORT
                        ).show()
                        onGoogleAuthSuccess?.invoke()
                    } else {
                        val msg = signInTask.exception?.localizedMessage
                            ?: "Gagal login dengan Google"
                        Toast.makeText(
                            this,
                            "Error Firebase: $msg",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } catch (e: ApiException) {
            // INI YANG PENTING: lihat statusCode-nya
            Toast.makeText(
                this,
                "Google Sign-In gagal: statusCode=${e.statusCode}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init FirebaseAuth (pakai versi non-ktx)
        auth = FirebaseAuth.getInstance()

        // Konfigurasi Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id otomatis dibuat dari google-services.json
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            MasakinTheme {
                val navController = rememberNavController()

                // Apa yang dilakukan setelah login Google sukses
                onGoogleAuthSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }

                MasakinNavGraph(
                    navController = navController,
                    onGoogleClick = {
                        val signInIntent = googleSignInClient.signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    }
                )
            }
        }
    }
}
