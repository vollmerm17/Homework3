package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Query("SELECT * FROM notes")
    fun selectAllNotes(): List<Note>

    @Query ("SELECT * FROM notes where id LIKE :search")
    fun selectOneNote(search: Long): Note

    @Query("SELECT * FROM notes where idStudent LIKE :search ")
    fun findStudentNotes(search: Long): List<Note>

    @Query("SELECT * FROM notes where id LIKE :search ")
    fun findNotes(search: Long): List<Note>

    @Update
    fun updateNote(note: Note)

    @Query("UPDATE notes SET title = :search,content = :searchString where id =:id ")
    fun updateN(search: String, searchString: String,id: Long)

    @Query("DELETE FROM notes WHERE id = :search  ")
    fun deleteN (search: Long)


}
