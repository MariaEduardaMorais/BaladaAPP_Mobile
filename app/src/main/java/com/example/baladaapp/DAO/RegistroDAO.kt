package com.example.baladaapp.DAO

import android.provider.BaseColumns
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.baladaapp.DBHelper.DBHelper
import com.example.baladaapp.DataBase.DatabaseContract
import com.example.baladaapp.DataBase.DatabaseContract.Entry
import com.example.baladaapp.Model.Registro

class RegistroDAO(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    fun inserirRegistro(registro: Registro): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.Entry.COLUMN_NAME_NOME, registro.nome)
            put(DatabaseContract.Entry.COLUMN_NAME_VALOR, registro.valor)
            put(DatabaseContract.Entry.COLUMN_NAME_DATA, registro.data)
            put(DatabaseContract.Entry.COLUMN_NAME_TIPO, registro.tipo)
            put(DatabaseContract.Entry.COLUMN_NAME_NOVA_COLUNA, registro.novaColuna)
            put(DatabaseContract.Entry.COLUMN_NAME_NOVA_COLUNA_2, registro.novaColuna2)
        }

        // Insere o registro e obtem o novo ID
        val novoId = db.insert(DatabaseContract.Entry.TABLE_NAME, null, values)

        db.close()

        return novoId
    }


    fun recuperarRegistros(): List<Registro> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            Entry.COLUMN_NAME_NOME,
            Entry.COLUMN_NAME_VALOR,
            Entry.COLUMN_NAME_DATA,
            Entry.COLUMN_NAME_TIPO,
            Entry.COLUMN_NAME_NOVA_COLUNA,
            Entry.COLUMN_NAME_NOVA_COLUNA_2
        )

        val cursor: Cursor = db.query(
            Entry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val registros = mutableListOf<Registro>()

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow("_id"))
                val nome = getString(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_NOME))
                val valor = getDouble(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_VALOR))
                val data = getString(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_DATA))
                val tipo = getString(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_TIPO))
                val novaColuna = getString(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_NOVA_COLUNA))
                val novaColuna2 = getInt(getColumnIndexOrThrow(DatabaseContract.Entry.COLUMN_NAME_NOVA_COLUNA_2))

                val registro = Registro(id, nome, valor, data, tipo, novaColuna, novaColuna2)
                registros.add(registro)
            }
        }

        cursor.close()
        db.close()

        return registros
    }

    fun limparRegistros() {
        val db = dbHelper.writableDatabase
        // Executa o comando SQL para excluir todos os registros
        db.delete(DatabaseContract.Entry.TABLE_NAME, null, null)
        db.close()
    }
}
