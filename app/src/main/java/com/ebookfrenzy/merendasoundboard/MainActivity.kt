package com.ebookfrenzy.merendasoundboard

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BUTTON_SIZE = 120
        private const val SPACING = 8

        private const val OPEN_SOUND = 234756

        private const val DIALOG_SAVE = "DialogSave"
    }

    private lateinit var recyclerView: RecyclerView
    private val mp = MediaPlayer()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SoundDatabase.load(this)

        createDbObserver()
        recyclerView = findViewById(R.id.sound_recycler_view)
        recyclerView.apply {
            adapter = SoundAdapter(SoundDatabase.listSound.value!!)

            val span = calcSpan()
            layoutManager = GridLayoutManager(this@MainActivity, span)
            addItemDecoration(GridSpacingItemDecoration(span, convertDpToPx(SPACING), false))
        }
    }

    private fun createDbObserver() {
        val observer = Observer<ArrayList<Sound>> {
            recyclerView.adapter?.notifyDataSetChanged()
        }

        SoundDatabase.listSound.observe(this, observer)
    }

    private fun calcSpan(): Int {
        // width = BUTTON_SIZE * x + (x-1) * spacing
        val x = BUTTON_SIZE + 8
        val width = resources.configuration.screenWidthDp + SPACING
        return (width + SPACING) / x
    }

    private fun convertDpToPx(dp: Int): Int {
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        )
        return px.toInt()
    }

    fun playSound(sound: Sound) {
        with(mp) {
            reset()
            if (sound is SoundResources) {
                setDataSource(resources.openRawResourceFd(sound.id))
            } else if(sound is SoundFile){
                setDataSource(this@MainActivity, sound.uri)
            }
            prepare()
            start()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.add_new_sound, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(R.id.menu_add_new_sound == item.itemId) {
            createNewSound()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == OPEN_SOUND && Activity.RESULT_OK == resultCode) {
            val dialog = SaveDialog.newInstance(data?.data.toString())
            dialog.show(supportFragmentManager, DIALOG_SAVE)
        }
    }

    private fun createNewSound() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "audio/*"
        }

        startActivityForResult(intent, OPEN_SOUND)
    }

    inner class SoundAdapter(private val soundList: ArrayList<Sound>)
        : RecyclerView.Adapter<SoundViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.button, parent, false)
            return SoundViewHolder(view)
        }

        override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
            val sound = soundList[position]
            holder.apply {
                button.text = sound.name
                this.sound = sound
            }
        }

        override fun getItemCount(): Int = soundList.size
    }

    inner class SoundViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val button: Button = view.findViewById(R.id.button_sound)
        var sound: Sound? = null

        init {
            button.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            playSound(sound!!)
        }
    }
}