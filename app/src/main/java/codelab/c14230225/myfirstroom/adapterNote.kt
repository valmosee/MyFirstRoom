package codelab.c14230225.myfirstroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import codelab.c14230225.myfirstroom.database.Note
import codelab.c14230225.myfirstroom.R

class adapterNote(private val listNote: MutableList<Note>) :
    RecyclerView.Adapter<adapterNote.ListViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback{
        fun delData(dtNote: Note)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        var tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsi)
        var tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)

        var _btnEdit : ImageButton = itemView.findViewById(R.id.btnEdit)
        var _btnDelete : ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val note : Note = listNote[position]

        holder.tvJudul.setText(note.judul)
        holder.tvDeskripsi.setText(note.deskripsi)
        holder.tvTanggal.setText(note.tanggal)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahData::class.java)
            intent.putExtra("notedID", note.id)
            intent.putExtra("addEdit", 1)

            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(note)
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    fun isiData(list: List<Note>){
        listNote.clear()
        listNote.addAll(list)

        notifyDataSetChanged()
    }
}
