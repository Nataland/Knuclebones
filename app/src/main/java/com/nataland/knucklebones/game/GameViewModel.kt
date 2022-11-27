package com.nataland.knucklebones.game

import androidx.lifecycle.ViewModel
import com.nataland.knucklebones.game.GameState.Companion.computeScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class GameState(
    val turnState: TurnState = TurnState.determineStartingPlayer(),
    val winState: WinState? = null,
    val player1Board: List<List<Int>> = List(3) { emptyList() },
    val player2Board: List<List<Int>> = List(3) { emptyList() },
    val currentDiceRoll: Int = 0,   // 1, 2, ..., 6
    val columnIndex: Int = 1        // 0, 1, 2
) {
    private val player1Score: Int
        get() = player1Board.computeScore()

    private val player2Score: Int
        get() = player2Board.computeScore()

    companion object {
        fun List<List<Int>>.computeScore() = sumOf {
            it.computeColumnScore()
        }

        fun List<Int>.computeColumnScore() = groupBy { diceValue ->
            diceValue
        }.values.sumOf { groupedValues ->
            groupedValues.sum() * groupedValues.size
        }
    }
}

enum class TurnState {
    PLAYER_1, PLAYER_2;

    companion object {
        fun determineStartingPlayer() = if (Random.nextInt(from = 1, until = 3) == 1) {
            PLAYER_1
        } else {
            PLAYER_2
        }
    }
}

enum class WinState {
    PLAYER_1_WON, PLAYER_2_WON, DRAW
}

class GameViewModel : ViewModel() {

    // Expose screen UI state
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    init {
        startTurn()
    }

    private fun startTurn() {
        _uiState.update { state ->
            state.copy(
                currentDiceRoll = Random.nextInt(from = 1, until = 7),
                columnIndex = 1
            )
        }
    }

    fun selectColumn(column: Int) {
        _uiState.update { state ->
            state.copy(
                columnIndex = column
            )
        }
    }

    fun endTurn() {
        _uiState.update { state ->
            if (state.winState != null) {
                state // Todo: show winner message
            } else if (state.turnState == TurnState.PLAYER_1) {
                val selectedColumn = state.player1Board[state.columnIndex]
                if (selectedColumn.size == 3) {
                    state
                } else {
                    val player1Board = state.player1Board.toMutableList()
                    player1Board[state.columnIndex] = selectedColumn + state.currentDiceRoll

                    val player2Board = state.player2Board.toMutableList()
                    val player2Index = 2 - state.columnIndex
                    val player2Column = player2Board[player2Index].toMutableList()
                    if (player2Column.contains(state.currentDiceRoll)) {
                        player2Column.removeAll { it == state.currentDiceRoll }
                    }
                    player2Board[player2Index] = player2Column
                    state.copy(
                        turnState = TurnState.PLAYER_2,
                        player1Board = player1Board.toList(),
                        player2Board = player2Board.toList(),
                        currentDiceRoll = Random.nextInt(from = 1, until = 7),
                        columnIndex = 1,
                        winState = if (player1Board.full()) {
                            determineWinner(player1Board, player2Board)
                        } else {
                            null
                        }
                    )
                }
            } else if (state.turnState == TurnState.PLAYER_2) {
                val selectedColumn = state.player2Board[state.columnIndex]
                if (selectedColumn.size == 3) {
                    state
                } else {
                    val player2Board = state.player2Board.toMutableList()
                    player2Board[state.columnIndex] = selectedColumn + state.currentDiceRoll

                    val player1Board = state.player1Board.toMutableList()
                    val player1Index = 2 - state.columnIndex
                    val player1Column = player1Board[player1Index].toMutableList()
                    if (player1Column.contains(state.currentDiceRoll)) {
                        player1Column.removeAll { it == state.currentDiceRoll }
                    }
                    player1Board[player1Index] = player1Column

                    state.copy(
                        turnState = TurnState.PLAYER_1,
                        player1Board = player1Board.toList(),
                        player2Board = player2Board.toList(),
                        currentDiceRoll = Random.nextInt(from = 1, until = 7),
                        columnIndex = 1,
                        winState = if (player2Board.full()) {
                            determineWinner(player1Board, player2Board)
                        } else {
                            null
                        }
                    )
                }
            } else {
                state
            }
        }
    }

    private fun determineWinner(board1: List<List<Int>>, board2: List<List<Int>>): WinState {
        val player1Score = board1.computeScore()
        val player2Score = board2.computeScore()
        return if (player1Score == player2Score) {
            WinState.DRAW
        } else if (player1Score > player2Score) {
            WinState.PLAYER_1_WON
        } else {
            WinState.PLAYER_2_WON
        }
    }

    private fun List<List<Int>>.full() = all { it.size == 3 }
}