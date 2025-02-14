package com.keniding.sanlove.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.keniding.sanlove.R
import com.keniding.sanlove.ui.common.navigation.DeepLinkHandler
import com.keniding.sanlove.ui.common.navigation.NavGraph
import com.keniding.sanlove.ui.common.theme.ValentineTheme
import com.keniding.sanlove.ui.common.viewmodel.AuthViewModel
import com.keniding.sanlove.ui.login.screens.LoginScreen
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel

class MyLove : ComponentActivity() {
    private val authViewModel: AuthViewModel by koinViewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            ValentineTheme {
                val authState by authViewModel.authState.collectAsState()

                if (authState.isAuthenticated) {
                    NavGraph(
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    LoginScreen(
                        onGoogleSignIn = {
                            val signInIntent = googleSignInClient.signInIntent
                            googleSignInLauncher.launch(signInIntent)
                        }
                    )
                }
            }
        }
    }

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { token ->
                    authViewModel.signInWithGoogle(token)
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    companion object {
        private const val TAG = "MyLove"
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)?.let { code ->
            DeepLinkHandler.setPendingCode(code)
        }
    }

    private fun handleIntent(intent: Intent?): String? {
        if (intent?.action == Intent.ACTION_VIEW) {
            val uri = intent.data
            if (uri?.scheme == "sanlove" && uri.host == "connect") {
                return uri.lastPathSegment
            }
        }
        return null
    }
}
