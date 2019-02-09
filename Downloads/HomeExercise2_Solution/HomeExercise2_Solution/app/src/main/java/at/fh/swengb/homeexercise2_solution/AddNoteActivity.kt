package at.fh.swengb.homeexercise2_solution

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import android.content.Intent



class AddNoteActivity : AppCompatActivity() {
    private lateinit var notesDatabase: NotesDatabase
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        notesDatabase = NotesDatabase.getDatabase(applicationContext)
        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val noteId = intent.getLongExtra("NoteId", -1L)
        if (-1L != noteId) {
            val title = notesDatabase.notesDao.selectOneNote(noteId).title
            val description = notesDatabase.notesDao.selectOneNote(noteId).content

            add_note_title.setText(title)
            add_note_content.setText(description)
        }

    }

    fun share(view: View) {

        val title = add_note_title.text.toString()
        val description = add_note_content.text.toString()

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "The title of the note is${title}, the content is ${description}")
        sendIntent.type = "text/plain"
        Intent.createChooser(sendIntent, "Share via")
        startActivity(sendIntent)
    }

    fun saveNote(v: View) {
       var noteId = intent.getLongExtra("NoteId", -1L)
       if (noteId == -1L){
           val title = add_note_title.text.toString()
           val description = add_note_content.text.toString()
           var idStu = sharedPreferences.getLong("SHARED_PREF_ID_KEY", -1L)

           if (title.isNotBlank() || description.isNotBlank()) {
               val note = Note(title, description,idStu)
               notesDatabase.notesDao.insertNote(note)
               finish()
           } else {
               Toast.makeText(this, "Please add a title and content", Toast.LENGTH_LONG).show()
           }
       }
        else {
           val title = add_note_title.text.toString()
           val description = add_note_content.text.toString()
           var idStu = sharedPreferences.getLong("SHARED_PREF_ID_KEY", -1L)
        //  notesDatabase.notesDao.updateN(title,description,idStu)

           val note = Note(title, description,idStu)
           notesDatabase.notesDao.updateNote(note)
           finish()
       }

    }
}
