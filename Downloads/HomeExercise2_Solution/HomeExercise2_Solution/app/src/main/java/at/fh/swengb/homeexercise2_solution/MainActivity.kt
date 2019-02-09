package at.fh.swengb.homeexercise2_solution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    companion object {
        val SHARED_PREF_NAME_KEY = "SHARED_PREF_NAME_KEY"
        val SHARED_PREF_AGE_KEY = "SHARED_PREF_AGE_KEY"
        val SHARED_PREF_PASSWORD_KEY = "SHARED_PREF_PASSWORD_KEY"
        val SHARED_PREF_ID_KEY = "SHARED_PREF_ID_KEY"
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesDatabase: NotesDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences(packageName, Context.MODE_PRIVATE)
        notesDatabase = NotesDatabase.getDatabase(applicationContext)

        val user = sharedPreferences.getString("SHARED_PREF_NAME_KEY", null)
        val passwd = sharedPreferences.getString("SHARED_PREF_PASSWORD_KEY", "")
        val id = sharedPreferences.getLong("SHARED_PREF_ID_KEY", -1L)
        val age = sharedPreferences.getInt( "SHARED_PREF_AGE_KEY", -2)


        if (user != null && passwd != "" && id != -1L && age != -2){

            val startNoteListActivityIntent = Intent(this, NoteListActivity::class.java)
            startActivity(startNoteListActivityIntent)
            finish()
        }
    }

    private fun getStudentsPasswort(name: String): Student{
        return notesDatabase.studentDao.findByName(name)
    }

    fun onSaveButtonClick(v: View) {
        val userName = user_name.text.toString()
        val password = password.text.toString()
        val find = notesDatabase.studentDao.findByNameList(userName)
        val valid = notesDatabase.studentDao.passwordValid(userName,password)
        //  val student = Student(userName, passwd,)

        if (userName.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Please enter a username and your password", Toast.LENGTH_LONG).show()
            return
        }

       if( find.isNullOrEmpty() && valid.isNullOrEmpty()){
           sharedPreferences.edit().putString(SHARED_PREF_NAME_KEY, userName).apply()
           sharedPreferences.edit().putString(SHARED_PREF_PASSWORD_KEY, password).apply()
           sharedPreferences.edit().putInt(SHARED_PREF_AGE_KEY, 0).apply()

           val startAgeIntent = Intent(this, Age::class.java)
           startActivity(startAgeIntent)
           finish()
        }

        if (find.isNotEmpty() && valid.isEmpty()){
                Toast.makeText(this, "The password is invalid", Toast.LENGTH_LONG).show()
                Log.e("Login","${getStudentsPasswort(userName).password},password wrong that's the right one!!")
                return
        }

        if(find.isNotEmpty() && valid.isNotEmpty()) {
            sharedPreferences.edit().putString(SHARED_PREF_NAME_KEY, userName).apply()
            sharedPreferences.edit().putString(SHARED_PREF_PASSWORD_KEY, password).apply()

            Toast.makeText(this, "Welcome back, ${getStudentsPasswort(userName).name}", Toast.LENGTH_LONG).show()
            Log.e("Login", "${getStudentsPasswort(userName).name} ,ist gefunden und hat das passwort ${getStudentsPasswort(userName).password}")

            logIn()
            }
    }

    private fun logIn() {
        val user = sharedPreferences.getString("SHARED_PREF_NAME_KEY", null)
        val password = sharedPreferences.getString("SHARED_PREF_PASSWORD_KEY", null)
        val studentId = getStudentsPasswort(user!!).studentId
        val age  = getStudentsPasswort(user).age

        sharedPreferences.edit().putLong(SHARED_PREF_ID_KEY, studentId
        ).apply()
        sharedPreferences.edit().putString(SHARED_PREF_NAME_KEY, user).apply()
        sharedPreferences.edit().putString(SHARED_PREF_PASSWORD_KEY, password).apply()
        sharedPreferences.edit().putInt(SHARED_PREF_AGE_KEY, age!!).apply()

        val startNoteListActivityIntent = Intent(this, NoteListActivity::class.java)
        startActivity(startNoteListActivityIntent)
        finish()
    }
}