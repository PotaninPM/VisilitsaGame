package com.example.visilitsa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.visilitsa.Word

@Composable
fun VisilitsaScreen() {

    var wordToGuess by remember { mutableStateOf(Word().getRandomWordRu()) }
    var correctLetters by remember { mutableStateOf(mutableSetOf<Int>()) }
    var wrongGuesses by remember { mutableStateOf(0) }
    var inputLetter by remember { mutableStateOf("") }
    var isGameOver by remember { mutableStateOf(false) }
    var isGameWon by remember { mutableStateOf(false) }

    val maxAttempts = wordToGuess.length - 2

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Text(
                text = when {
                    isGameOver -> "Вы проиграли!"
                    isGameWon -> "Вы выиграли!"
                    else -> "Игра идет"
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                fontWeight = FontWeight.Bold,
                color = when {
                    isGameOver -> Color.Red
                    isGameWon -> Color.Green
                    else -> MaterialTheme.colorScheme.primary
                }
            )

            WordVisible(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                word = wordToGuess,
                correctLetters = correctLetters
            )

            Text(
                text = "Ошибки: $wrongGuesses из $maxAttempts",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (!isGameOver && !isGameWon) {
                OutlinedTextField(
                    value = inputLetter,
                    onValueChange = { if (it.length <= 1) inputLetter = it },
                    label = { Text("Введите букву") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            Button(
                onClick = {
                    if (isGameOver || isGameWon) {
                        wordToGuess = Word().getRandomWordRu()

                        correctLetters.clear()
                        wrongGuesses = 0
                        inputLetter = ""
                        isGameOver = false
                        isGameWon = false
                    } else if (inputLetter.isNotEmpty()) {

                        val letter = inputLetter[0]
                        if (wordToGuess.contains(letter)) {
                            wordToGuess.forEachIndexed { index, char ->
                                if (char == letter) {
                                    correctLetters.add(index)
                                }
                            }
                            if (correctLetters.size == wordToGuess.length) {
                                isGameWon = true
                            }
                        } else {
                            wrongGuesses++
                            if (wrongGuesses >= maxAttempts) {
                                isGameOver = true
                            }
                        }
                        inputLetter = ""
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(if (isGameOver || isGameWon) "Новая игра" else "Угадать")
            }
        }
    }
}

@Composable
fun WordVisible(
    modifier: Modifier,
    word: String,
    correctLetters: Set<Int>
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        items(word.length) { index ->
            val char = if (correctLetters.contains(index)) word[index].toString() else "_"
            val color: Color = if (correctLetters.contains(index)) Color.Yellow else MaterialTheme.colorScheme.secondaryContainer
            LetterCard(char, color)
        }
    }
}

@Composable
fun LetterCard(letter: String, color: Color) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp, 60.dp)
            .border(width = 2.dp, color = color, shape = MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
