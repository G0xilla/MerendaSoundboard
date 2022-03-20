package com.ebookfrenzy.merendasoundboard.db

import android.database.Cursor
import android.database.CursorWrapper
import com.ebookfrenzy.merendasoundboard.Sound
import com.ebookfrenzy.merendasoundboard.SoundFile

class SoundCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getSound(): Sound {
        val name = getString(getColumnIndex(SoundTable.Cols.NAME))
        val uriString = getString(getColumnIndex(SoundTable.Cols.PATH))

        return SoundFile(name, uriString)
    }
}