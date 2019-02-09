package at.fh.swengb.homeexercise2_solution

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class NotesandStudents() {
    @Embedded
    lateinit var student: Student
    @Relation(entity = Note::class,entityColumn = "idStudent", parentColumn = "studentId")
    lateinit var notes: List <Note>

}