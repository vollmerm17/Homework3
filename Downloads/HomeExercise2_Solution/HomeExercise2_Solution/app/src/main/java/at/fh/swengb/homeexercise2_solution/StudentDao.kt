package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.*

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Query("DELETE from students")
    fun deleteAll()

    //evtl damit die passwort abfrage
    @Query("SELECT * from students where name LIKE :searchString")
    fun findByName(searchString: String): Student

    @Query("SELECT * from students where name LIKE :searchString")
    fun findByNameList(searchString: String): List<Student>

    @Query("SELECT * from students where studentId LIKE :search")
    fun findById(search: Long): Student

    //@Query("SELECT * from students where name LIKE  :searchString ")
    //fun showPassword (searchString: String)

    @Query("SELECT * FROM students WHERE name LIKE :search AND password LIKE :searchString")
    fun passwordValid(search: String,searchString: String): List<Student>


    @Query("SELECT * FROM students")
    fun findAllStudentsAndNotes(): List<NotesandStudents>?
}