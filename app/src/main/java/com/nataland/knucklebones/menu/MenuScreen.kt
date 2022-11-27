package com.nataland.knucklebones.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onStartClick: () -> Unit,
    onRulesClick: () -> Unit,
    onAboutClick: () -> Unit,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(48.dp)
            .fillMaxSize()
    ) {

        MenuButton(onClick = onStartClick, text = "Start")
        Spacer(modifier = Modifier.padding(12.dp))
        MenuButton(onClick = onRulesClick, text = "Rules")
        Spacer(modifier = Modifier.padding(12.dp))
        MenuButton(onClick = onAboutClick, text = "About")
    }
}

@Composable
private fun MenuButton(onClick: () -> Unit, text: String) {
    Button(
        onClick, modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text,
            modifier = Modifier.padding(8.dp)
        )
    }
}