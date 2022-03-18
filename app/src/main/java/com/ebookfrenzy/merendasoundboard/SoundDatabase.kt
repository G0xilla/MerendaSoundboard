package com.ebookfrenzy.merendasoundboard

object SoundDatabase {
    val listSound = mutableListOf<Sound>()

    init {
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

    private fun MutableList<Sound>.add(name: String, id: Int) {
        add(Sound(name, id))
    }
}