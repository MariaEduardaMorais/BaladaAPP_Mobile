package com.example.baladaapp.DataBase

import android.provider.BaseColumns

object DatabaseContract {
    object Entry : BaseColumns {
        const val TABLE_NAME = "registro"
        const val COLUMN_NAME_NOME = "nome"
        const val COLUMN_NAME_VALOR = "valor"
        const val COLUMN_NAME_DATA = "data"
        const val COLUMN_NAME_TIPO = "tipo"
        const val COLUMN_NAME_NOVA_COLUNA = "nova_coluna"
        const val COLUMN_NAME_NOVA_COLUNA_2 = "nova_coluna_2"

        // Definindo a constante SQL_CREATE_ENTRIES
        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME_NOME TEXT," +
                    "$COLUMN_NAME_VALOR REAL," +
                    "$COLUMN_NAME_DATA TEXT," +
                    "$COLUMN_NAME_TIPO TEXT," +
                    "$COLUMN_NAME_NOVA_COLUNA TEXT," +
                    "$COLUMN_NAME_NOVA_COLUNA_2 INTEGER)"
    }
}

