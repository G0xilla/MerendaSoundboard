package com.ebookfrenzy.merendasoundboard

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import com.ebookfrenzy.merendasoundboard.db.SoundBaseHelper
import com.ebookfrenzy.merendasoundboard.db.SoundCursorWrapper
import com.ebookfrenzy.merendasoundboard.db.SoundTable
import java.util.ArrayList

object SoundDatabase {
    val listSound = MutableLiveData<ArrayList<Sound>>()
    private var db: SQLiteDatabase? = null

    init {
        listSound.value = ArrayList()

        listSound.apply {
            add("a to", R.raw.a_to)
            add("ale dobry", R.raw.ale_dobry)
            add("celkem trapne", R.raw.celkem_trapne)
            add("do huby", R.raw.do_huby)
            add("dobre vis co", R.raw.dobre_vis_co)
            add("ja neco reknu", R.raw.ja_neco_reknu)
            add("klidne ti dam", R.raw.klidne_ti_dam)
            add("nebudu resit", R.raw.nebudu_resit)
            add("nejsem jediny", R.raw.nejsem_jediny)
            add("nemam nic", R.raw.nemam_nic)
            add("opakujes", R.raw.opakujes)
            add("za pocitacem", R.raw.za_pocitacem)
            add("zbytek si", R.raw.zbytek_si)
        }
    }

    private fun add(name: String, id: Int) {
        listSound.value?.add(SoundResources(name, id))
    }

    private fun add(name: String, uri: String) {
        listSound.value?.add(SoundFile(name, uri))
        listSound.value = listSound.value
    }

    fun load(context: Context) {
        db = SoundBaseHelper(context.applicationContext).writableDatabase

        val cursor = queryCrimes(null, null)
        cursor.use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                listSound.value?.add(cursor.getSound())
                cursor.moveToNext()
            }
        }
        cursor.close()
    }

    fun addSound(name: String, uri: String) {
        val values = ContentValues().apply {
            put(SoundTable.Cols.NAME, name)
            put(SoundTable.Cols.PATH, uri)
        }

        db!!.insert(SoundTable.NAME, null, values)

        add(name, uri)
    }

    fun removeSound(sound: Sound) {
        if(sound is SoundFile) {
            listSound.value!!.remove(sound)
            listSound.value = listSound.value
            db?.delete(SoundTable.NAME, "name=?", arrayOf(sound.name))
        }
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): SoundCursorWrapper {
        val cursor = db?.query(
            SoundTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null)

        return SoundCursorWrapper(cursor!!)
    }
}