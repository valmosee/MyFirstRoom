package codelab.c14230225.myfirstroom

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

        val _xmlAdd = findViewById<TextView>(R.id.txtAddTitle)
        val _xmlUpd = findViewById<TextView>(R.id.txtUpdTitle)

        var iID : Int = 0
        var iAddEdit : Int = 0

        iID = intent.getIntExtra("noteId", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0){
            _btnAdd.visibility = View.VISIBLE
            _btnEdit.visibility = View.GONE
            _inJudul.isEnabled = true

            _xmlAdd.visibility = View.VISIBLE
            _xmlUpd.visibility = View.GONE
        } else {
            _btnAdd.visibility = View.GONE
            _btnEdit.visibility = View.VISIBLE
            _inJudul.isEnabled = false

            _xmlAdd.visibility = View.GONE
            _xmlUpd.visibility = View.VISIBLE

            CoroutineScope(Dispatchers.IO).async {
                val noteItem = DB.funnoteDao().getNote(iID)
                _inJudul.setText(noteItem.judul)
                _inDeskripsi.setText(noteItem.deskripsi)
            }
        }

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
            Toast.makeText(this ,"Add Successful", Toast.LENGTH_SHORT).show()
        }

        _btnEdit.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                DB.funnoteDao().update(
                    _inJudul.text.toString(),
                    _inDeskripsi.text.toString(),
                    iID
                )
                finish()
            }
            Toast.makeText(this ,"Update Successful", Toast.LENGTH_SHORT).show()
        }
    }
}