package at.fh.swengb.homeexercise2_solution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_age.*
import kotlinx.android.synthetic.main.activity_main.*

class Age : AppCompatActivity() {

    private lateinit var notesDatabase: NotesDatabase
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        notesDatabase = NotesDatabase.getDatabase(applicationContext)

    }

    fun getStudents(name: String): Student{
        return notesDatabase.studentDao.findByName(name)
    }


    fun newUser(v: View){
        val passwd = sharedPreferences.getString(MainActivity.SHARED_PREF_PASSWORD_KEY,"")
        val age = editext_age.text.toString().toIntOrNull()
        val userName = sharedPreferences.getString(MainActivity.SHARED_PREF_NAME_KEY,null)

        if (age == null){
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_LONG).show()
        }

        if (age != null){

            val student = Student(userName!!, passwd!!,age)

            notesDatabase.studentDao.insertStudent(student)
            Log.e("Login","${getStudents(userName!!).name},new user")
            logInAge()
        }
    }

    private fun logInAge() {
        val userName = sharedPreferences.getString("SHARED_PREF_NAME_KEY", null)
        val studentId = getStudents(userName!!).studentId
        val age = editext_age.text.toString().toInt()

        sharedPreferences.edit().putLong(MainActivity.SHARED_PREF_ID_KEY, studentId).apply()
        sharedPreferences.edit().putInt(MainActivity.SHARED_PREF_AGE_KEY, age).apply()
        val startNoteListActivityIntent = Intent(this, NoteListActivity::class.java)
        startActivity(startNoteListActivityIntent)
        finish()
    }
}
