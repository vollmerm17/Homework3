package at.fh.swengb.homeexercise2_solution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_note_list.*

class NoteListActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesDatabase: NotesDatabase
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        notesDatabase = NotesDatabase.getDatabase(applicationContext)
        populateUserInfo()

        val idStu = sharedPreferences.getLong(MainActivity.SHARED_PREF_ID_KEY,-1L)
        val dbItems = getStudentNotes(idStu)


        notesAdapter = NotesAdapter({
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("NoteId", it.id)
            startActivity(intent)},{
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Delete")


            dialogBuilder.setMessage("Do you really wanna delete this note?")
            dialogBuilder.setPositiveButton("yes"){_,_->notesDatabase.notesDao.deleteN(it.id)

            val dbItems = getStudentNotes(idStu)
                notesAdapter.updateData(dbItems)}

            dialogBuilder.setNegativeButton("No", null)
            dialogBuilder.show()
            notesAdapter.updateList(notesDatabase.notesDao.findStudentNotes(it.idStudent))
        })
        //notesAdapter = NotesAdapter{startActivity(Intent(this,AddNoteActivity::class.java))}
        notes_recycler_view.adapter = notesAdapter
        notes_recycler_view.layoutManager = LinearLayoutManager(this)


        notesAdapter.updateData(dbItems)
    }

    private fun getStudentNotes(id: Long): List<Note>{
        return notesDatabase.notesDao.findStudentNotes(id)
    }

    override fun onResume() {
        val idStu = sharedPreferences.getLong("SHARED_PREF_ID_KEY", -1L)
        notesAdapter.updateList(notesDatabase.notesDao.findStudentNotes(idStu))

        super.onResume()
    }

    private fun populateUserInfo() {
        val userName = sharedPreferences.getString(MainActivity.SHARED_PREF_NAME_KEY, null)
        val age = sharedPreferences.getInt(MainActivity.SHARED_PREF_AGE_KEY, -2)

        if (userName.isNullOrBlank() || age == -2) {
            user_info.text = "Invalid username or age"
        } else {
            user_info.text = "Notes for ${userName},${age}"
        }
    }

    fun addNote(v: View) {
        val addNoteIntent = Intent(this, AddNoteActivity::class.java)
        startActivity(addNoteIntent)
    }

    fun logOut(v:View){
        sharedPreferences.edit().remove(MainActivity.SHARED_PREF_NAME_KEY).apply()
        sharedPreferences.edit().remove(MainActivity.SHARED_PREF_PASSWORD_KEY).apply()
        sharedPreferences.edit().remove(MainActivity.SHARED_PREF_ID_KEY).apply()
        sharedPreferences.edit().remove(MainActivity.SHARED_PREF_AGE_KEY).apply()
        val addNoteIntent = Intent(this, MainActivity::class.java)
        startActivity(addNoteIntent);
    }
}
