package com.ebookfrenzy.merendasoundboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment

class SaveDialog : AppCompatDialogFragment() {

    companion object {
        private const val URI = "uri"

        fun newInstance(uri: String): SaveDialog {
            val args = Bundle()
            args.putString(URI, uri)
            val fragment = SaveDialog()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var nameEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        AlertDialog.Builder(activity).apply {
            setTitle("Name of sound")

            val view = LayoutInflater.from(activity).inflate(R.layout.save_dialog, null)
            nameEditText = view.findViewById(R.id.name_edit_text)
            setView(view)
            setNegativeButton("cancel") { _, _ -> }
            setPositiveButton("ok") { _, _ ->
                Log.d("tvoje", nameEditText.text.toString())
                if (nameEditText.text.isNotEmpty()) {
                    SoundDatabase
                        .addSound(nameEditText.text.toString(), requireArguments().getString(URI)!!)
                }
            }
            return create()
        }

    }
}