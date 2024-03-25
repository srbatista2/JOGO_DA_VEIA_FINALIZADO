package com.example.jogodaveiafinalizado

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private var isPlayer1 = true
    private var gameActive = true

    private lateinit var center: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView8: ImageView
    private lateinit var imageView9: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        center = findViewById(R.id.top)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)
        imageView8 = findViewById(R.id.imageView8)
        imageView9 = findViewById(R.id.imageView9)

        val composeView: ComposeView = findViewById(R.id.pl)
        composeView.setContent {
            ResetGameButton {
                resetGame()
            }
        }

        configureBox(center)
        configureBox(imageView2)
        configureBox(imageView3)
        configureBox(imageView4)
        configureBox(imageView5)
        configureBox(imageView6)
        configureBox(imageView7)
        configureBox(imageView8)
        configureBox(imageView9)
    }

    @Composable
    fun ResetGameButton(onReset: () -> Unit) {
        Button(onClick = onReset, modifier = Modifier.padding(16.dp)) {
            Text(text = "LIMPAR TELA", fontSize = 18.sp)
        }
    }

    private fun resetGame() {
        val boxes = listOf(center, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9)
        boxes.forEach { box ->
            box.setImageDrawable(null)
            box.tag = null
        }
        isPlayer1 = true
        gameActive = true
    }

    private fun configureBox(box: ImageView) {
        box.setOnClickListener {
            if (box.tag == null && gameActive) {
                box.setImageResource(if (isPlayer1) R.drawable.baseline_add_circle_24 else R.drawable.baseline_close_24)
                box.tag = if (isPlayer1) 1 else 2
                isPlayer1 = !isPlayer1
                checkGameState()
            }
        }
    }

    private fun checkGameState() {
        if (playerWin(1)) {
            gameActive = false
            showMessage("Jogador 1 ganhou!")
        } else if (playerWin(2)) {
            gameActive = false
            showMessage("Jogador 2 ganhou!")
        } else if (checkDraw()) {
            gameActive = false
            showMessage("Empate!")
        }
    }

    private fun playerWin(value: Int): Boolean {
        val conditions = listOf(
            listOf(center, imageView2, imageView3),
            listOf(imageView4, imageView5, imageView6),
            listOf(imageView7, imageView8, imageView9),
            listOf(center, imageView4, imageView7),
            listOf(imageView2, imageView5, imageView8),
            listOf(imageView3, imageView6, imageView9),
            listOf(center, imageView5, imageView9),
            listOf(imageView3, imageView5, imageView7)
        )

        return conditions.any { line ->
            line.all { box -> box.tag == value }
        }
    }

    private fun checkDraw(): Boolean {
        val allBoxes = listOf(center, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9)
        return allBoxes.all { it.tag != null } && gameActive
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
