package com.example.colorboxes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // Declarando variáveis para o container, contador e cores das caixas.
    private lateinit var container: RelativeLayout // Container onde as caixas serão adicionadas.
    private lateinit var clickCountView: TextView // TextView para mostrar a quantidade de cliques.
    private var clickCount = 0 // Contador de cliques.
    // Array de cores para as caixas.
    private val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA)
    private val gameTime = 60000L // Tempo total do jogo (60 segundos).

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define o layout que será usado pela atividade.
        setContentView(R.layout.activity_main)

        // Conecta as variáveis com os elementos da interface.
        container = findViewById(R.id.container)
        clickCountView = findViewById(R.id.clickCountView)
        // Inicializa o texto do contador de cliques.
        clickCountView.text = "Cliques: 0"

        // Adiciona a primeira caixa colorida à tela assim que o layout for montado.
        container.post {
            addRandomColoredBox()
        }

        // Inicia a contagem regressiva do jogo.
        startGameTimer()
    }

    // Função para iniciar a contagem regressiva.
    private fun startGameTimer() {
        object : CountDownTimer(gameTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Aqui você poderia atualizar o texto do cronômetro, se necessário.
            }

            override fun onFinish() {
                // Mostra uma mensagem quando o tempo acabar e o jogo terminar.
                Toast.makeText(this@MainActivity, "Tempo acabou! Você clicou $clickCount vezes.", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun addRandomColoredBox() {
        // Limpa o container para que apenas uma caixa seja clicável por vez.
        container.removeAllViews()

        // Cria e configura uma nova caixa colorida.
        val box = View(this).apply {
            val size = dpToPx(150) // Define o tamanho da caixa.
            // Configura os parâmetros de layout, incluindo o tamanho e a posição.
            val params = RelativeLayout.LayoutParams(size, size)
            params.leftMargin = Random.nextInt(container.width - size)
            params.topMargin = Random.nextInt(container.height - size)
            layoutParams = params
            // Define uma cor aleatória para a caixa.
            setBackgroundColor(colors[Random.nextInt(colors.size)])

            // Define o que acontece quando a caixa é clicada.
            setOnClickListener {
                clickCount++ // Incrementa o contador de cliques.
                clickCountView.text = "Cliques: $clickCount" // Atualiza o texto do contador.
                addRandomColoredBox() // Adiciona uma nova caixa colorida.
            }
        }

        // Adiciona a caixa ao container.
        container.addView(box)
    }

    // Função para converter dp (density-independent pixels) para pixels.
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}
