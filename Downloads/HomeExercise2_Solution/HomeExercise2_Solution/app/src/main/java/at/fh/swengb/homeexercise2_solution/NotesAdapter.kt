package at.fh.swengb.homeexercise2_solution

import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(val clickListener: (note: Note) -> Unit,val clickLongListener: (note: Note) -> Unit) : RecyclerView.Adapter<NotesViewHolder>() {

    private var notesList: List<Note> = mutableListOf<Note>()

    fun updateList(newList: List<Note>) {
        notesList = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(view,clickListener,clickLongListener)
    }

    override fun getItemCount() : Int {
        return notesList.size
    }

    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
        val item = notesList[position]
        viewHolder.bind(item)

    }

    fun updateData(newList: List<Note>) {
        notesList = newList.toMutableList()
        notifyDataSetChanged()
    }


}

class NotesViewHolder(view: View,val clickListener: (notes: Note) -> Unit,val  clickLongListener: (note: Note) -> Unit) : RecyclerView.ViewHolder(view) {
    fun bind(item: Note) {
        itemView.note_title.text = item.title
        itemView.note_content.text = item.content
        itemView.setOnClickListener { clickListener(item) }
        itemView.setOnLongClickListener {
            clickLongListener(item)
            true
        }
    }

}
