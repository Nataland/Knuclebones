package com.nataland.knucklebones.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nataland.knucklebones.R
import com.nataland.knucklebones.game.GameState.Companion.computeColumnScore

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column {
        // Player 1
        Board(
            board = uiState.player1Board,
            modifier = Modifier
                .weight(1f)
                .rotate(180f)
                .background(colorResource(id = R.color.player_1_bg)),
            currentDiceRoll = uiState.currentDiceRoll,
            selectedColumn = uiState.columnIndex,
            isActive = uiState.turnState == TurnState.PLAYER_1,
            onColumnSelected = viewModel::selectColumn,
            onAccept = viewModel::endTurn
        )

        // Player 2
        Board(
            board = uiState.player2Board,
            modifier = Modifier
                .weight(1f)
                .background(colorResource(id = R.color.player_2_bg)),
            currentDiceRoll = uiState.currentDiceRoll,
            selectedColumn = uiState.columnIndex,
            isActive = uiState.turnState == TurnState.PLAYER_2,
            onColumnSelected = viewModel::selectColumn,
            onAccept = viewModel::endTurn
        )
    }
}

@Composable
fun Board(
    board: List<List<Int>>,
    modifier: Modifier,
    currentDiceRoll: Int,
    selectedColumn: Int,
    isActive: Boolean,
    onColumnSelected: (Int) -> Unit,
    onAccept: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            board.forEachIndexed { i, column ->
                if (i != 0) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    val score = column.computeColumnScore().toString()
                    Text(
                        text = score,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(72.dp).rotate(180f)
                    )
                    Text(
                        text = score,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(72.dp)
                    )
                    Column(
                        modifier = if (isActive && selectedColumn == i) {
                            Modifier
                                .clickable {
                                    onColumnSelected(i)
                                }
                                .border(2.dp, Color.LightGray)
                        } else if (isActive) {
                            Modifier.clickable {
                                onColumnSelected(i)
                            }
                        } else {
                            Modifier
                        }
                    ) {
                        for (row in 0 until 3) {
                            if (row <= column.lastIndex) {
                                Dice(column[row])
                            } else {
                                Dice(0)
                            }
                        }
                    }
                }
            }
        }

        if (isActive) {
            Button(onClick = onAccept) {
                Text(text = "Accept")
            }

            DiceImage(value = currentDiceRoll)

            // Todo: show current board score
        }
    }
}

@Composable
fun Dice(value: Int) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .background(Color.Gray)
    ) {
        DiceImage(value = value, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun DiceImage(value: Int, modifier: Modifier = Modifier) {
    val id = value.toDiceDrawableId()
    id?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "$value",
            modifier = modifier
                .size(72.dp)
                .background(Color.White)
        )
    }
}

private fun Int.toDiceDrawableId(): Int? = when (this) {
    1 -> R.drawable.dice1
    2 -> R.drawable.dice2
    3 -> R.drawable.dice3
    4 -> R.drawable.dice4
    5 -> R.drawable.dice5
    6 -> R.drawable.dice6
    else -> null
}