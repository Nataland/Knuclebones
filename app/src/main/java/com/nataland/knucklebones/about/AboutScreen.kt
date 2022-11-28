package com.nataland.knucklebones.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nataland.knucklebones.R
import com.nataland.knucklebones.ui.theme.Link

@Composable
fun AboutScreen(
    onBackPressed: () -> Unit,
    onIcons8LinkPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.about))
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.back_button_cd))
                    }
                },
            )
        }, content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space8)),
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.space24))
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.made_by))

                    Row {
                        Text(text = stringResource(R.string.icons_by))
                        Text(
                            text = stringResource(R.string.icons8),
                            color = Link,
                            modifier = Modifier.clickable(onClick = onIcons8LinkPressed)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space24)))

                Image(
                    painter = painterResource(id = R.drawable.danny),
                    contentDescription = stringResource(R.string.danny_photo_cd),
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.space48))
                        .clip(CircleShape)
                        .border(4.dp, Color.White, CircleShape)
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space16)))

                Text(text = stringResource(R.string.thanks))
            }
        }
    )
}