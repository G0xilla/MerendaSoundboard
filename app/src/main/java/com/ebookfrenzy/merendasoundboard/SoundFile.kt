package com.ebookfrenzy.merendasoundboard

import android.net.Uri

class SoundFile(name: String, uri: String): Sound(name) {
    val uri: Uri = Uri.parse(uri)
}