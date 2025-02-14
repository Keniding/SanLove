package com.keniding.sanlove.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.keniding.sanlove.ui.common.navigation.NavGraph
import com.keniding.sanlove.ui.common.theme.ValentineTheme

class MyLove : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValentineTheme {
                NavGraph(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
