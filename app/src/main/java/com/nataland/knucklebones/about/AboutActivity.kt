package com.nataland.knucklebones.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nataland.knucklebones.ui.theme.KnucklebonesTheme


class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnucklebonesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AboutScreen(::finish) {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://icons8.com")
                        )
                        startActivity(browserIntent)
                    }
                }
            }
        }
    }
}
