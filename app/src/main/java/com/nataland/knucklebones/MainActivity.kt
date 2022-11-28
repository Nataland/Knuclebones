package com.nataland.knucklebones

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nataland.knucklebones.about.AboutActivity
import com.nataland.knucklebones.game.GameActivity
import com.nataland.knucklebones.menu.MenuScreen
import com.nataland.knucklebones.rules.RulesActivity
import com.nataland.knucklebones.ui.theme.KnucklebonesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KnucklebonesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MenuScreen(
                        onStartClick = { startActivity(Intent(this, GameActivity::class.java)) },
                        onRulesClick = { startActivity(Intent(this, RulesActivity::class.java)) },
                        onAboutClick = { startActivity(Intent(this, AboutActivity::class.java)) }
                    )
                }
            }
        }
    }
}
