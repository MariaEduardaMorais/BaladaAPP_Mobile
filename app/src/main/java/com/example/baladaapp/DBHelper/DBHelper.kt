package com.example.baladaapp.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.baladaapp.DataBase.DatabaseContract
import com.example.baladaapp.DataBase.DatabaseContract.Entry.SQL_CREATE_ENTRIES

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualização do banco de dados se necessário
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE ${DatabaseContract.Entry.TABLE_NAME} ADD COLUMN ${DatabaseContract.Entry.COLUMN_NAME_NOVA_COLUNA} TEXT")
        }
    }

    companion object {
        const val DATABASE_NAME = "Balada.db"
        const val DATABASE_VERSION = 2
    }
}

