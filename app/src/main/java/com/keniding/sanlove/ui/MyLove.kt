package com.keniding.sanlove.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.keniding.sanlove.ui.common.navigation.DeepLinkHandler
import com.keniding.sanlove.ui.common.navigation.NavGraph
import com.keniding.sanlove.ui.common.theme.ValentineTheme

class MyLove : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        handleIntent(intent)?.let { code ->
            DeepLinkHandler.setPendingCode(code)
        }

        setContent {
            ValentineTheme {
                NavGraph(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
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
