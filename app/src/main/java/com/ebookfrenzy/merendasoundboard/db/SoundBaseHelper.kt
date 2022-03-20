package com.ebookfrenzy.merendasoundboard.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SoundBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        private const val VERSION = 1
        private const val DATABASE_NAME = "soundBase.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table ${SoundTable.NAME} (" +
                " _id integer primary key autoincrement, " +
                "${SoundTable.Cols.NAME}, " +
                "${SoundTable.Cols.PATH})")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}