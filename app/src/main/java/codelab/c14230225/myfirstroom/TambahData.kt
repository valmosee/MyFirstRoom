package codelab.c14230225.myfirstroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import codelab.c14230225.myfirstroom.database.Note
import codelab.c14230225.myfirstroom.database.NoteRoomDatabase
import codelab.c14230225.myfirstroom.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahData : AppCompatActivity() {
    val DB : NoteRoomDatabase = NoteRoomDatabase.getDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var tanggal : String = getCurrentDate()

        val _inJudul = findViewById<EditText>(R.id.etJudul)
        val _inDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val _btnAdd = findViewById<Button>(R.id.btnTambah)
        val _btnEdit = findViewById<Button>(R.id.btnUpdate)

        _btnAdd.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().insert(
                    Note(
                        0,
                        judul = _inJudul.text.toString(),
                        deskripsi = _inDeskripsi.text.toString(),
                        tanggal
                    )
                )
            }
        }
    }
}