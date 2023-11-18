package com.example.baladaapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baladaapp.DAO.RegistroDAO

class Registros : AppCompatActivity() {

    private lateinit var textViewCaixaResposta: TextView
    private lateinit var textViewGastoResposta: TextView
    private lateinit var textViewTotalResposta: TextView
    private lateinit var buttonVoltar: Button
    private lateinit var buttonLimparBanco: Button

    private val registroDAO by lazy { RegistroDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registros)

        textViewCaixaResposta = findViewById(R.id.textViewCaixaResposta)
        textViewGastoResposta = findViewById(R.id.textViewGastoResposta)
        textViewTotalResposta = findViewById(R.id.textViewTotalResposta)
        buttonLimparBanco = findViewById(R.id.buttonLimparBanco)
        buttonVoltar = findViewById(R.id.buttonVoltar)

        buttonVoltar.setOnClickListener {
            finish()
        }

        buttonLimparBanco.setOnClickListener {
            limparBancoDeDados()
            atualizarDados()
        }

        // Atualiza os textos com os dados do banco de dados
        atualizarDados()
    }

    private fun atualizarDados() {
        val registros = registroDAO.recuperarRegistros()

        // Calcula o total, caixa e gasto com base nos registros
        val totalEntradas = registros.filter { it.tipo == "Entrada" }.sumByDouble { it.valor }
        val totalSaidas = registros.filter { it.tipo == "Saída" }.sumByDouble { it.valor }

        // Calcula o caixa com base nas entradas e saídas
        val caixa = totalEntradas

        // Calcula o gasto total
        val gasto = totalSaidas

        // Calcula a diferença entre entradas e saídas
        val total = (totalEntradas + totalSaidas)

        // Atualiza os TextViews
        textViewCaixaResposta.text = String.format("R$%.2f", caixa)
        textViewGastoResposta.text = String.format("R$%.2f", gasto)
        textViewTotalResposta.text = String.format("R$%.2f", total)
    }


    // Função para limpar o banco de dados
    private fun limparBancoDeDados() {
        // Chama o método para excluir todos os registros do banco de dados
        registroDAO.limparRegistros()
        Toast.makeText(this, "Banco de dados limpo!", Toast.LENGTH_SHORT).show()
    }
}
