package codelab.c14230225.myfirstroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import codelab.c14230225.myfirstroom.database.Note
import codelab.c14230225.myfirstroom.database.NoteRoomDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var DB : NoteRoomDatabase
    private lateinit var adapterN : adapterNote
    private var arNote : MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = NoteRoomDatabase.getDatabase(this)
        val _fabAdd = findViewById<ImageButton>(R.id.fabAdd)
        val _rvNotes : RecyclerView = findViewById<RecyclerView>(R.id.rvNotes)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahData::class.java))
        }

        adapterN = adapterNote(arNote)

        _rvNotes.layoutManager = LinearLayoutManager(this)
        _rvNotes.adapter = adapterN

        adapterN.setOnItemClickCallback(
            object : adapterNote.OnItemClickCallback{
                override fun delData(dtnote : Note){
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funnoteDao().delete(dtnote)
                        val note = DB.funnoteDao().selectAll()
                        Log.d("data ROOM2", note.toString())

                        withContext(Dispatchers.Main){
                            adapterN.isiData(note)
                        }
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val note = DB.funnoteDao().selectAll()
            Log.d("data ROOM", note.toString())

            adapterN.isiData(note)
        }
    }
}