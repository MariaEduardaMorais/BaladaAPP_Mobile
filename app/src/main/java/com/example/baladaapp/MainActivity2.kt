package com.example.baladaapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baladaapp.DAO.RegistroDAO
import com.example.baladaapp.Model.Registro
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity2 : AppCompatActivity() {

    private lateinit var editTextNome: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var editTextDate: EditText
    private lateinit var radioGroupTipo: RadioGroup
    private lateinit var buttonCadastro: Button
    private lateinit var buttonResumo: Button
    private lateinit var buttonSair: Button

    private val registroDAO by lazy { RegistroDAO(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNome = findViewById(R.id.editTextNome)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextDate = findViewById(R.id.editTextDate)
        radioGroupTipo = findViewById(R.id.radioGroupTipo)
        buttonCadastro = findViewById(R.id.buttonCadastro)
        buttonResumo = findViewById(R.id.buttonResumo)
        buttonSair = findViewById(R.id.buttonSair)

        editTextDate.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePickerDialog()
            }
        }

        buttonCadastro.setOnClickListener {
            cadastrarRegistro()
        }

        buttonResumo.setOnClickListener {
            startActivity(Intent(this, Registros::class.java))
        }

        buttonSair.setOnClickListener {
            finishAffinity()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                editTextDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun cadastrarRegistro() {
        // Obtem os valores dos componentes
        val nome = editTextNome.text.toString()
        val valor = editTextNumber.text.toString().toDouble()

        // Verifica qual RadioButton está selecionado
        val selectedRadioButtonId = radioGroupTipo.checkedRadioButtonId
        val radioButton: RadioButton = findViewById(selectedRadioButtonId)

        // Verifica se um RadioButton está selecionado
        if (selectedRadioButtonId == -1) {
            // Nenhum RadioButton selecionado
            Toast.makeText(this, "Selecione Entrada ou Saída", Toast.LENGTH_SHORT).show()
            return
        }

        // Determina se é uma entrada ou saída com base no RadioButton selecionado
        val isEntrada = radioButton.id == R.id.radioButtonEntrada
        val tipo = if (isEntrada) "Entrada" else "Saída"

        // Ajusta o valor com base no tipo (entrada ou saída)
        val valorAjustado = if (isEntrada) valor else -valor

        // Formata a data
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val data = dateFormat.format(Calendar.getInstance().time)

        // Cria um objeto Registro com os valores
        val registro = Registro(id = 0, nome, valorAjustado, data, tipo, novaColuna = "", novaColuna2 = 0)

        // Insere o registro no banco de dados
        val novoId = registroDAO.inserirRegistro(registro)

        // Limpa os campos após a inserção
        editTextNome.text.clear()
        editTextNumber.text.clear()
        editTextDate.text.clear()

        // Limpa a seleção do RadioGroup
        radioGroupTipo.clearCheck()
    }
}
